package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/resources/auth"
	"net/http"

	"github.com/gin-gonic/gin"
)

func addClientsRoutes(api *gin.RouterGroup) {
	clients := api.Group("/clients")
	{
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
	}
}
