package routes

import (
	"github.com/gin-gonic/gin"
)

func addCartRoutes(rg *gin.RouterGroup) {
	cart := rg.Group("/cart")

	cart.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all cart items",
		})
	})

	cart.POST("/", func(c *gin.Context) {
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
