package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
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

		// productIDStr := c.Param("product_id")

		// productID, err := strconv.Atoi(productIDStr)
		// if err != nil {
		// 	c.JSON(500, gin.H{"error": "Invalid user product ID"})
		// 	return
		// }

		productValue, exists := c.Get("product")
		if !exists {
			c.JSON(500, gin.H{"error": "Product not found"})
			return
		}
		product := productValue.(*models.Product)

		quantityStr := c.Param("quantity")

		quantity, err := strconv.Atoi(quantityStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid quantity"})
			return
		}

		err = database.AddProduct(product.ID, uint(id), uint(quantity))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Add item to cart",
		})
	})

	cart.DELETE("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), func(c *gin.Context) {
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
			c.JSON(500, gin.H{"error": "Invalid product ID"})
			return
		}

		err = database.DeleteProductFromCart(uint(productID), uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Delete cart item",
		})
	})
}
