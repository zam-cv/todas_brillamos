package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"time"

	"github.com/gin-gonic/gin"
)

func addBuyRoutes(rg *gin.RouterGroup) {
	buy := rg.Group("/buy")

	buy.POST("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		cart, err := database.GetCartByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		orders := []*models.Orders{}
		deliveryDate := time.Now().AddDate(0, 0, 7)
		status := "En camino"

		for _, product := range cart {
			order := models.Orders{
				ProductID:    product.ProductID,
				ClientID:     product.ClientID,
				Quantity:     product.Quantity,
				DeliveryDate: deliveryDate,
				Status:       status,
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
