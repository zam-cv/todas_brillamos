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
func GetNotificationsByClientID(clientID uint) ([]models.Notifications, error) {
	var notifications []models.Notifications
	err := db.Where("client_id = ?", clientID).Find(&notifications).Error
	return notifications, err
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
