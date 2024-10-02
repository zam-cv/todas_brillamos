package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"net/http"
	"strconv"
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

		idClients := database.GetAllClientsIDs()

		now := time.Now()
		notification.Date = now

		for _, id := range idClients {
			newNotification := models.Notifications{
				Title:       notification.Title,
				Description: notification.Description,
				Date:        now,
				ClientID:    id,
			}
			err := database.CreateNotification(&newNotification)
			if err != nil {
				c.Status(http.StatusInternalServerError)
				return
			}
		}
		c.Status(http.StatusCreated)
	})

	// notifications.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
	// 	id, _ := c.MustGet("clientID").(uint)

	// 	notifications, err := database.GetNotificationsByClientID(id)
	// 	if err != nil {
	// 		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
	// 		return
	// 	}

	// 	c.JSON(http.StatusOK, notifications)
	// })

	notifications.POST("/:clientID", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		userId := c.Param("clientID")
		userIdInt, err := strconv.Atoi(userId)

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		var notification models.Notifications
		if err := c.ShouldBindJSON(&notification); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		notification.ClientID = uint(userIdInt)

		now := time.Now()
		notification.Date = now

		err = database.CreateNotificationByClientID(&notification)
		if err != nil {
			c.Status(http.StatusInternalServerError)
			return
		}

	})

	notifications.GET("/:clientID", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		//id, _ := c.MustGet("clientID").(uint)
		clientIDParam := c.Param("clientID")

		clientID, err := strconv.ParseUint(clientIDParam, 10, 32)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid client ID"})
			return
		}

		notifications, err := database.GetNotificationsByClientID(uint(clientID))
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, notifications)
	})

	notifications.GET("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		notifications, err := database.GetAllNotifications()
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, notifications)
	})
}
