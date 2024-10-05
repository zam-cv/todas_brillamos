package middlewares

import (
	"backend/database"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

// ExistsClientMiddleware es un middleware que verifica si un cliente existe en la base de datos.
// Si el cliente no existe, responde con un error 404 y aborta la solicitud.
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

// GetClientID es un middleware que obtiene el ID del cliente de la solicitud.
// Si el ID del cliente no está presente o es inválido, responde con un error 401 y aborta la solicitud.
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

		client, err := database.GetClientByUserID(id)
		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		ctx.Set("clientID", uint(client.ID))
		ctx.Next()
	}
}
