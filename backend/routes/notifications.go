// Contiene las operaciones relacionadas con las notificaciones.
// Autores:
//   - Mariana Balderrábano
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

// Crea una nueva notificación en la base de datos para todos los clientes.
// Devuelve un error en caso de que ocurra.
func addNotificationsRoutes(rg *gin.RouterGroup) {
	notifications := rg.Group("/notifications")

	// Agrega una nueva notificación a la base de datos para todos los clientes
	notifications.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var notification models.Notifications
		if err := c.ShouldBindJSON(&notification); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		idClients := database.GetAllClientsIDs()

		// asigna la fecha a la notificación
		now := time.Now()
		notification.Date = now

		// Ciclo para crear notificaciones para cada cliente
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

	// Agrega una nueva notificación a la base de datos asociada a un cliente
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

	// Obtiene todas las notificaciones de la base de datos asociadas a un cliente
	notifications.GET("/:clientID", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		clientID := c.Param("clientID")
		clientIDInt, err := strconv.Atoi(clientID)

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}
		groupedNotifications, err := database.GetNotificationsByClientID(uint(clientIDInt))
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, groupedNotifications)
	})

	// Obtiene todas las notificaciones de la base de datos de todos los clientes
	notifications.GET("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		notifications, err := database.GetAllNotifications()
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, notifications)
	})
}
