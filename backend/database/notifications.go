// Contiene las operaciones relacionadas con las notificaciones.
// Autores:
//   - Mariana Balderrábano
package database

import (
	"backend/models"
)

// Crea una nueva notificación en la base de datos para todos los clientes.
// Devuelve un error en caso de que ocurra.
func CreateNotification(notification *models.Notifications) error {
	if err := db.Create(notification).Error; err != nil {
		return err
	}
	return nil
}

// Obtiene todas las notificaciones de un usuario de la base de datos.
// Devuelve un slice de models.Notifications y un error en caso de que ocurra.
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

// Crea una nueva notificación en la base de datos asociada a un cliente.
// Devuelve un error en caso de que ocurra.
func CreateNotificationByClientID(notification *models.Notifications) error {
	if err := db.Create(notification).Error; err != nil {
		return err
	}
	return nil
}

// Todas las notificaciones de la base de datos de todos los clientes.
// Devuelve un slice de models.Notifications y un error en caso de que ocurra.
func GetAllNotifications() ([]models.Notifications, error) {
	var notifications []models.Notifications
	err := db.Find(&notifications).Error
	return notifications, err
}
