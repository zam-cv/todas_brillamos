package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"fmt"
	"math"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/stripe/stripe-go"
	"github.com/stripe/stripe-go/paymentintent"
)

func addBuyRoutes(rg *gin.RouterGroup) {
	buy := rg.Group("/buy")

	buy.POST("/create-payment-intent", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		cart, err := database.GetAllCartByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		amount := calculateTotalAmount(cart)

		params := &stripe.PaymentIntentParams{
			Amount:   stripe.Int64(amount),
			Currency: stripe.String("mxn"),
			Params: stripe.Params{
				Metadata: map[string]string{
					"clientID": fmt.Sprintf("%d", id),
					"amount":   fmt.Sprintf("%d", amount),
				},
			},
		}

		pi, err := paymentintent.New(params)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"clientSecret": pi.ClientSecret,
		})
	})

	buy.POST("/confirm", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		var paymentData struct {
			PaymentIntentID string `json:"paymentIntentId"`
		}
		if err := c.BindJSON(&paymentData); err != nil {
			c.JSON(400, gin.H{"error": "Invalid payment data"})
			return
		}

		pi, err := paymentintent.Get(paymentData.PaymentIntentID, nil)
		if err != nil {
			c.JSON(400, gin.H{"error": "Invalid PaymentIntent"})
			return
		}

		if pi.Metadata["clientID"] != fmt.Sprintf("%d", id) {
			c.JSON(403, gin.H{"error": "Unauthorized"})
			return
		}

		cart, err := database.GetAllCartByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		recalculatedAmount := calculateTotalAmount(cart)

		if recalculatedAmount != pi.Amount {
			c.JSON(400, gin.H{"error": "Amount mismatch"})
			return
		}

		if pi.Status != stripe.PaymentIntentStatusSucceeded {
			c.JSON(400, gin.H{"error": "Payment not successful"})
			return
		}

		orders := []*models.Orders{}
		deliveryDate := time.Now().AddDate(0, 0, 5).Format("2006-01-02")
		now := time.Now().Format("2006-01-02")
		status := "En camino"

		for _, product := range cart {
			order := models.Orders{
				ProductID:          product.Product.ProductID,
				ClientID:           id,
				Quantity:           product.Quantity,
				OrderReceivedDate:  now,
				DeliveryDate:       deliveryDate,
				Status:             status,
				PreparingOrderDate: nil,
				ShippedDate:        nil,
			}
			orders = append(orders, &order)
		}

		err = database.CreateOrders(orders)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		err = database.DeleteAllProductsFromCart(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Purchase completed",
		})
	})
}

func calculateTotalAmount(cart []models.CartItem) int64 {
	var total float64
	for _, product := range cart {
		total += product.Product.Price * float64(product.Quantity)
	}
	return int64(math.Round(total * 100))
}
