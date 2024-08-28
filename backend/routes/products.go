package routes

import (
	"github.com/gin-gonic/gin"
)

func addProductRoutes(rg *gin.RouterGroup) {
	product := rg.Group("/products")

	product.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all products",
		})
	})

	product.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get product by id",
		})
	})

	product.POST("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Create product",
		})
	})

	product.PUT("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Update product",
		})
	})

	product.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete product",
		})
	})

	product.GET("/categories", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all categories",
		})
	})

	product.GET("/recommended", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get recommended products",
		})
	})
}
