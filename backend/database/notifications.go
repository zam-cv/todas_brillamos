/*
 * Backend-database: Querys necesarias para la tabla de notificaciones
 * @author: Mariana Balderrábano
 */
package database

import (
	"backend/models"
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
func GetNotificationsByClientID(clientID uint) ([]models.GroupedNotifications, error) {
	var notifications []models.Notifications
	err := db.Where("client_id = ?", clientID).Find(&notifications).Error
	if err != nil {
		return nil, err
	}

	grouped := make(map[string][]models.NotificationsGet)

	for _, notification := range notifications {
		dateKey := notification.Date.Format("2006-01-02") // Formato de la fecha
		grouped[dateKey] = append(grouped[dateKey], models.NotificationsGet{
			Title:       notification.Title,
			Description: notification.Description,
			ClientID:    notification.ClientID,
		})
	}

	var result []models.GroupedNotifications
	for date, notifs := range grouped {
		result = append(result, models.GroupedNotifications{
			Date:          date,
			Notifications: notifs,
		})
	}

	return result, nil
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
