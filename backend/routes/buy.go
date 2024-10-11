// Rutas relacionadas con las compras.
// Autores:
//   - Jennyfer Jasso
//   - Carlos Zamudio

package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"fmt"
	"log"
	"math"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/stripe/stripe-go"
	"github.com/stripe/stripe-go/paymentintent"
)

// Añade las rutas relacionadas con las compras al grupo de rutas proporcionado.
func addBuyRoutes(rg *gin.RouterGroup) {
	buy := rg.Group("/buy")

	// POST /buy/create-payment-intent - Crea una intención de pago
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
			"clientSecret":    pi.ClientSecret,
			"paymentIntentId": pi.ID,
		})
	})

	// POST /buy/confirm - Confirma una compra
	buy.POST("/confirm", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		var paymentData struct {
			PaymentIntentID string `json:"paymentIntentId"`
		}
		if err := c.BindJSON(&paymentData); err != nil {
			log.Printf("Error binding JSON: %v", err)
			c.JSON(http.StatusBadRequest, gin.H{"error": "Datos de pago inválidos"})
			return
		}

		pi, err := paymentintent.Get(paymentData.PaymentIntentID, nil)
		if err != nil {
			log.Printf("Error getting PaymentIntent: %v", err)
			c.JSON(http.StatusBadRequest, gin.H{"error": "Intención de pago inválida"})
			return
		}

		if pi.Metadata["clientID"] != fmt.Sprintf("%d", id) {
			log.Println("Unauthorized: ClientID mismatch")
			c.JSON(http.StatusForbidden, gin.H{"error": "No autorizado"})
			return
		}

		cart, err := database.GetAllCartByClientID(id)
		if err != nil {
			log.Printf("Error getting cart: %v", err)
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		recalculatedAmount := calculateTotalAmount(cart)

		if recalculatedAmount != pi.Amount {
			log.Printf("Amount mismatch: expected %d, got %d", recalculatedAmount, pi.Amount)
			c.JSON(http.StatusBadRequest, gin.H{"error": "Discrepancia en el monto"})
			return
		}

		if pi.Status != stripe.PaymentIntentStatusSucceeded {
			log.Printf("Payment not successful: status is %s", pi.Status)
			c.JSON(http.StatusBadRequest, gin.H{"error": "El pago no fue exitoso"})
			return
		}

		orders := []*models.Orders{}
		deliveryDate := time.Now().AddDate(0, 0, 5).Format("2006-01-02") // Fecha de entrega en 5 días
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
			log.Printf("Error creating orders: %v", err)
			c.JSON(http.StatusInternalServerError, gin.H{"error": "Error al crear las órdenes"})
			return
		}

		err = database.DeleteAllProductsFromCart(id)
		if err != nil {
			log.Printf("Error deleting products from cart: %v", err)
			c.JSON(http.StatusInternalServerError, gin.H{"error": "Error al limpiar el carrito"})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"message": "Compra completada",
		})
	})
}

// Calcula el monto total de los productos en el carrito.
// Devuelve el monto total en centavos.
func calculateTotalAmount(cart []models.CartItem) int64 {
	var total float64
	for _, product := range cart {
		total += product.Product.Price * float64(product.Quantity)
	}
	return int64(math.Round(total * 100))
}
