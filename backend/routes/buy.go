package routes

import (
	"github.com/gin-gonic/gin"
)

func addBuyRoutes(rg *gin.RouterGroup) {
	buy := rg.Group("/buy")

	buy.POST("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Buy product",
		})
	})

	buy.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Cancel purchase",
		})
	})
}
