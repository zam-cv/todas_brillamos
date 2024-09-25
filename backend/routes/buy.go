package routes

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
)

func addBuyRoutes(rg *gin.RouterGroup) {
	buy := rg.Group("/buy")

	buy.POST("/", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		idStr, exists := c.Get("userID")
		if !exists {
			c.JSON(500, gin.H{"error": "User ID not found"})
			return
		}

		id, err := strconv.Atoi(idStr.(string))
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid user ID"})	
			return
		}
		
		cart, err := database.GetCartByClientID(uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		orders := []*models.Orders{}
		deliveryDate := time.Now().AddDate(0, 0, 7)
		status := "En camino"

		for _, product := range cart {
			order := models.Orders{
				ProductID: product.ProductID, 
				ClientID: product.ClientID, 
				Quantity: product.Quantity,
				DeliveryDate: deliveryDate,
				Status: status,
			}
			orders = append(orders, &order)
		}

		err = database.CreateOrders(orders)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		err = database.DeleteAllProductsFromCart(uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Purchase completed",
		})

	})

	buy.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Cancel purchase",
		})
	})
}
