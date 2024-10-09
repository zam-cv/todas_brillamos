/*
 * Backend-routes: Código que determina los endpoints de client y sus rutas
 * @author: Carlos Zamudio
 * @co-author: Mariana Balderrábano
 */
package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type PasswordUpdate struct {
	OldPassword string `json:"old_password" binding:"required"`
	NewPassword string `json:"new_password" binding:"required"`
}

/*
 * Función para agregar rutas de los clientes
 */
func addClientsRoutes(api *gin.RouterGroup) {
	clients := api.Group("/clients")
	{
		// Endpoint GET que obtiene el nombre completo de un cliente dado por parámetros
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

		// Endpoint GET que obtiene los detalles de un cliente
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

		// Endpoint PUT que actualiza la contraseña de un cliente
		clients.PUT("/update-password", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
			userID, exists := c.MustGet("userID").(string)
			if !exists {
				c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
				return
			}

			id, err := strconv.Atoi(userID)
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}

			var passwordUpdate PasswordUpdate
			if err := c.ShouldBindJSON(&passwordUpdate); err != nil {
				c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
				return
			}

			user, err := database.GetUserByID(id)
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}

			if !auth.CheckPasswordHash(passwordUpdate.OldPassword, user.Password) {
				c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid password"})
				return
			}

			hashedPassword, err := auth.HashPassword(passwordUpdate.NewPassword)
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}

			err = database.UpdateUserPassword(id, hashedPassword)
			if err != nil {
				c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
				return
			}

			c.Status(http.StatusOK)
		})

		// Endpoint PUT que actualiza los detalles de un cliente
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

		clients.GET("/getInfo/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
			clientIDStr := c.Param("id")
			clientID, err := strconv.Atoi(clientIDStr)
			if err != nil {
				c.JSON(400, gin.H{"error": "Invalid client ID"})
				return
			}

			clientInfo, err := database.GetOthersClientDetails(uint(clientID))
			if err != nil {
				c.JSON(404, gin.H{"error": "Not Found"})
				return
			}

			c.JSON(http.StatusOK, clientInfo)

		})

		clients.GET("/getIDS", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
			ids := database.GetAllClientsIDs()

			c.JSON(http.StatusOK, ids)
		})
	}
}
