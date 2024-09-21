package routes

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

func addNotificationsRoutes(rg *gin.RouterGroup) {
	notifications := rg.Group("/notifications")

	notifications.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var notification models.Notifications
		if err := c.ShouldBindJSON(&notification); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		users, err := database.GetAllUsers()

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		now := time.Now()
		notification.Date = now

		for _, user := range users {
			notification.ClientID = user.ID
			err := database.CreateNotification(&notification)
			if err != nil {
				c.Status(http.StatusInternalServerError)
				return
			}
		}
		c.Status(http.StatusCreated)
	})

	notifications.GET("", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		clientID := c.MustGet("userID").(uint)
		notifications, err := database.GetNotificationsByClientID(clientID)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, notifications)
	})
}
