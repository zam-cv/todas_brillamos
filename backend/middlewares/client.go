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

func GetClientID() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		idStr, exists := ctx.Get("userID")
		if !exists {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		id, err := strconv.Atoi(idStr.(string))
		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		_, err = database.GetClientByUserID(id)
		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		ctx.Set("clientID", uint(id))
		ctx.Next()
	}
}
