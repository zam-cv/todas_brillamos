package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/resources/auth"
	"strconv"

	"github.com/gin-gonic/gin"
)

func addCartRoutes(rg *gin.RouterGroup) {
	cart := rg.Group("/cart")

	cart.GET("/", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		id, exists := c.Get("userID")
		if !exists {
			c.JSON(500, gin.H{"error": "User ID not found"})
			return
		}

		products, err := database.GetCartByClientID(id.(uint))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"products": products,
		})
	})

	cart.POST("/:product_id/:quantity", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), func(c *gin.Context) {
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

		productIDStr := c.Param("product_id")

		productID, err := strconv.Atoi(productIDStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid user product ID"})
			return
		}

		quantityStr := c.Param("quantity")

		quantity, err := strconv.Atoi(quantityStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid quantity"})
			return
		}

		err = database.AddProduct(uint(id), uint(productID), uint(quantity))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Add item to cart",
		})
	})

	cart.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete cart item",
		})
	})
}
