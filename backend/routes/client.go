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
