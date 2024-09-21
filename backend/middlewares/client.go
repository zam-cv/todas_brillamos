package middlewares

import (
	"backend/database"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

func ExistsClientMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		clientID := c.Param("client_id")
		id, err := strconv.Atoi(clientID)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid client ID"})
			c.Abort()
			return
		}
		
		client, err := database.GetClientByID(id)
		if err != nil {
			c.JSON(http.StatusNotFound, gin.H{"error": "Client not found"})
			c.Abort()
			return
		}

		c.Set("client", client)
		c.Next()
	}
}