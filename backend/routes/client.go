package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"net/http"

	"github.com/gin-gonic/gin"
)

func addClientsRoutes(api *gin.RouterGroup) {
	clients := api.Group("/clients")
	{
		clients.GET("/fullname", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
			clientID, exists := c.MustGet("clientID").(uint)
			if !exists {
				c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
				return
			}

			fullname, err := database.GetFullNameByClientID(int(clientID))
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}

			c.JSON(http.StatusOK, fullname)
		})

		clients.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
			clientID, exists := c.MustGet("clientID").(uint)
			if !exists {
				c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
				return
			}

			clientDetails, err := database.GetClientDetails(clientID)
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}
			c.JSON(http.StatusOK, clientDetails)
		})

		clients.PUT("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
			clientID, exists := c.MustGet("clientID").(uint)
			if !exists {
				c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
				return
			}

			var clientDetails models.ClientDetails
			if err := c.ShouldBindJSON(&clientDetails); err != nil {
				c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
				return
			}

			err := database.UpdateClientDetails(clientID, &clientDetails)
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}
			c.JSON(http.StatusOK, clientDetails)
		})
	}
}
