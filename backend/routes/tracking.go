package routes

import (
	"github.com/gin-gonic/gin"
)

func addTrackingRoutes(rg *gin.RouterGroup) {
	tracking := rg.Group("/tracking")

	tracking.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all tracking information",
		})
	})

	tracking.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking information",
		})
	})

	tracking.PUT("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Update tracking information",
		})
	})

	tracking.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete tracking information",
		})
	})

	tracking.GET("/status", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking status",
		})
	})

	tracking.GET("/location", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking location",
		})
	})
}
