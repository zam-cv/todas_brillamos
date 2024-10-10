/*
 * Backend-database: Querys necesarias para la tabla de notificaciones
 * @author: Mariana Balderrábano
 */
package database

import (
	"backend/models"
	"strings"
)

/*
 * Función que crea una nueva notificación en la base de datos para todos los clientes
 * @param notification: Puntero a la notificación a crear
 * @return error: Error en caso de que exista
 */
func CreateNotification(notification *models.Notifications) error {
	if err := db.Create(notification).Error; err != nil {
		return err
	}
	return nil
}

/*
 * Función que obtiene todas las notificaciones de un usuario de la base de datos y las arregla por fecha
 * @param clientID: ID del cliente
 * @return []models.Notifications: Arreglo de notificaciones
 * @return error: Error en caso de que exista
 */
// func GetNotificationsByClientID(clientID uint) ([]models.Notifications, error) {
// 	var notifications []models.Notifications
// 	err := db.Where("client_id = ?", clientID).Find(&notifications).Error
// 	return notifications, err
// }

var monthTranslations = map[string]string{
	"Jan": "Ene",
	"Feb": "Feb",
	"Mar": "Mar",
	"Apr": "Abr",
	"May": "May",
	"Jun": "Jun",
	"Jul": "Jul",
	"Aug": "Ago",
	"Sep": "Sep",
	"Oct": "Oct",
	"Nov": "Nov",
	"Dec": "Dic",
}

func translateMonth(date string) string {
	for eng, esp := range monthTranslations {
		date = strings.ReplaceAll(date, eng, esp)
	}
	return date
}

// func GetNotificationsByClientID(clientID uint) ([]models.GroupedNotifications, error) {
// 	var notifications []models.Notifications
// 	err := db.Where("client_id = ?", clientID).Order("date DESC").Find(&notifications).Error
// 	if err != nil {
// 		return nil, err
// 	}

// 	grouped := make(map[string][]models.NotificationsGet)

// 	for _, notification := range notifications {
// 		dateKey := notification.Date.Format("2006-01-02")
// 		displayDate := translateMonth(notification.Date.Format("Jan 2"))
// 		hour := notification.Date.Format("03:04 PM")

// 		grouped[dateKey] = append(grouped[dateKey], models.NotificationsGet{
// 			Hour:        hour,
// 			Title:       notification.Title,
// 			Description: notification.Description,
// 			ClientID:    notification.ClientID,

// 		})
// 	}

// 	// var result []models.GroupedNotifications
// 	// for date, notifs := range grouped {
// 	// 	result = append(result, models.GroupedNotifications{
// 	// 		Date:          date,
// 	// 		Notifications: notifs,
// 	// 	})
// 	// }

// 	var groupedNotifications []models.GroupedNotifications
// 	for _, notifications := range grouped {
// 		groupedNotifications = append(groupedNotifications, models.GroupedNotifications{
// 			//Date:          notifications[0].DisplayDate, // Tomamos la fecha formateada del primer elemento
// 			Date:
// 			Notifications: notifications,
// 		})
// 	}

// 	return groupedNotifications, nil
// }

func GetNotificationsByClientID(clientID uint) ([]models.GroupedNotifications, error) {
	var notifications []models.Notifications
	err := db.Where("client_id = ?", clientID).Order("date DESC").Find(&notifications).Error
	if err != nil {
		return nil, err
	}

	grouped := make(map[string][]models.NotificationsGet)

	for _, notification := range notifications {
		// Aquí formateamos la fecha en español para el agrupamiento
		displayDate := translateMonth(notification.Date.Format("Jan 2"))
		hour := notification.Date.Format("03:04 PM")

		grouped[displayDate] = append(grouped[displayDate], models.NotificationsGet{
			Hour:        hour,
			Title:       notification.Title,
			Description: notification.Description,
			ClientID:    notification.ClientID,
		})
	}

	var groupedNotifications []models.GroupedNotifications
	for date, notifications := range grouped {
		groupedNotifications = append(groupedNotifications, models.GroupedNotifications{
			Date:          date, // Usamos la fecha traducida como clave del grupo
			Notifications: notifications,
		})
	}

	return groupedNotifications, nil
}

/*
 * Función que crea una nueva notificación en la base de datos asociada a un cliente
 * @param notification: Puntero a la notificación a crear
 * @return error: Error en caso de que exista
 */
func CreateNotificationByClientID(notification *models.Notifications) error {
	if err := db.Create(notification).Error; err != nil {
		return err
	}
	return nil
}

/*
 * Función que obtiene todas las notificaciones de la base de datos de todos los clientes
 * @return []models.Notifications: Arreglo de notificaciones
 * @return error: Error en caso de que exista
 */
func GetAllNotifications() ([]models.Notifications, error) {
	var notifications []models.Notifications
	err := db.Find(&notifications).Error
	return notifications, err
}
