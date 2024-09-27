package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources"
	"backend/resources/auth"

	"github.com/gin-gonic/gin"
)

func addOthersRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/others")

	group.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)
		others, err := database.GetOthersByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, others)
	})

	group.POST("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)
		other := &models.Other{}
		if err := c.ShouldBindJSON(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		if err := resources.ValidateStruct(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		other.ClientID = id
		_, err := database.CreateOtherByClientID(other)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, other)
	})

	group.PUT("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		// actualizar datos adicionales del cliente
		id, _ := c.MustGet("clientID").(uint)
		other := &models.Other{}
		if err := c.ShouldBindJSON(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		if err := resources.ValidateStruct(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		other.ClientID = id
		err := database.UpdateOthersByClientID(other)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, other)
	})
}
