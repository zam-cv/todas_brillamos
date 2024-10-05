/*
 * Backend-routes: Código que determina los endpoints de notificaciones y sus rutas
 * @author: Mariana Balderrábano
 */
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

/*
 * Función para agregar rutas de notificaciones a la API (Post, Get)
 */
func addNotificationsRoutes(rg *gin.RouterGroup) {
	notifications := rg.Group("/notifications")

	// Endpoint POST que agrega una nueva notificación a la base de datos para todos los clientes
	notifications.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var notification models.Notifications
		if err := c.ShouldBindJSON(&notification); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		idClients := database.GetAllClientsIDs()

		// asigna la fecha actual a la notificación
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

	// Endpoint POST que agrega una nueva notificación a la base de datos asociada a un cliente
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

	// Endpoint GET que obtiene todas las notificaciones de un cliente de la base de datos
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

	// Endpoint GET que obtiene todas las notificaciones de la base de datos de todos los clientes
	notifications.GET("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		notifications, err := database.GetAllNotifications()
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, notifications)
	})
}
