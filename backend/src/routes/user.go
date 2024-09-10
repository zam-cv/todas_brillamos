package routes

import (
	"github.com/gin-gonic/gin"
)

func addUserRoutes(rg *gin.RouterGroup) {
	user := rg.Group("/users")

	user.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all users",
		})
	})

	user.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get user by id",
		})
	})
}
