package routes

import (
	"github.com/gin-gonic/gin"
)

func addConfigRoutes(rg *gin.RouterGroup) {
	config := rg.Group("/config")
	payment := config.Group("/payment")
	notification := config.Group("/notification")

	payment.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all payment methods",
		})
	})

	payment.POST("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Create payment method",
		})
	})

	payment.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete payment method",
		})
	})

	notification.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all notification types",
		})
	})

	notification.PUT("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Update notification type",
		})
	})
}
