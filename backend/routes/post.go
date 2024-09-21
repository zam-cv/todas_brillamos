package routes

import (
	"backend/models"
	"backend/database"

	"github.com/gin-gonic/gin"
)

func addPostRoutes(rg *gin.RouterGroup) {
	post := rg.Group("/posts")

	post.POST("", func(c *gin.Context) {
		var post models.Post
		if err := c.ShouldBindJSON(&post); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		id, err := database.CreatePost(&post)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}
		c.JSON(201, gin.H{"id": id})
	})
}