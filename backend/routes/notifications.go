// Rutas de notificaciones
// Autores:
//   - Mariana Balderrábano

package routes

import (
	"backend/config"
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"backend/resources/mail"
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
)

// Agrega las rutas de notificaciones a la API

func addNotificationsRoutes(rg *gin.RouterGroup) {
	notifications := rg.Group("/notifications")

	// POST /notifications - Agrega una nueva notificación para todos los clientes
	notifications.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var notification models.Notifications
		if err := c.ShouldBindJSON(&notification); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		emailsClients := database.GetAllClientsEmails()

		// Asigna la fecha a la notificación.
		now := time.Now()
		notification.Date = now

		// Ciclo para crear notificaciones para cada cliente
		for _, client := range emailsClients {
			mail.SendEmail(mail.EmailPayload{
				Title:  notification.Title,
				Body:   notification.Description,
				Emails: []string{client.Email},
			}, config.ApiKeyMailer, config.EmailMailer)

			newNotification := models.Notifications{
				Title:       notification.Title,
				Description: notification.Description,
				Date:        now,
				ClientID:    client.ID,
			}
			err := database.CreateNotification(&newNotification)
			if err != nil {
				c.Status(http.StatusInternalServerError)
				return
			}
		}
		c.Status(http.StatusCreated)
	})

	// POST /notifications - Agrega una nueva notificación a la base de datos asociada a un cliente
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

		// define la fecha actual para la notificación
		now := time.Now()
		notification.Date = now

		err = database.CreateNotificationByClientID(&notification)
		if err != nil {
			c.Status(http.StatusInternalServerError)
			return
		}
	})

	//// GET /notifications - Obtiene todas las notificaciones de un usuario
	notifications.GET("/all", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		idClient, exists := c.MustGet("clientID").(uint)
		if !exists {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized: client ID not found"})
			return
		}

		notifications, err := database.GetNotificationsByClientID(uint(idClient))
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		if len(notifications) == 0 {
			notifications = []models.GroupedNotifications{}
		}

		c.JSON(http.StatusOK, notifications)
	})

	// GET /notifications - Obtiene todas las notificaciones de todos los usuarios
	notifications.GET("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		notifications, err := database.GetAllNotifications()
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, notifications)
	})

	// PUT /notifications - Obtener todas las notficaciones de un usuario que no han sido leídas
	notifications.GET("/unread", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		idClient, exists := c.MustGet("clientID").(uint)
		if !exists {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized: client ID not found"})
			return
		}

		number, err := database.GetUnreadNotificationsCount(uint(idClient))
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, gin.H{"unread": number})
	})
}
