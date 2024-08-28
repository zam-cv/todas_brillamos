package routes

import (
	"github.com/gin-gonic/gin"
)

func addBlogRoutes(rg *gin.RouterGroup) {
	blog := rg.Group("/blog")

	blog.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all blog posts",
		})
	})

	blog.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get blog post by id",
		})
	})

	blog.POST("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Create blog post",
		})
	})

	blog.PUT("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Update blog post",
		})
	})

	blog.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete blog post",
		})
	})
}
