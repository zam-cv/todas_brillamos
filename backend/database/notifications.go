// Contiene las operaciones relacionadas con las notificaciones.
// Autores:
//   - Mariana Balderrábano
package database

import (
	"backend/models"
	"strings"
)

// Crea una nueva notificación en la base de datos para todos los clientes.
// Devuelve un error en caso de que ocurra.
func CreateNotification(notification *models.Notifications) error {
	if err := db.Create(notification).Error; err != nil {
		return err
	}
	return nil
}

// Traducciones de los meses en inglés a español
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

// Traduce los meses en inglés a español
func translateMonth(date string) string {
	for eng, esp := range monthTranslations {
		date = strings.ReplaceAll(date, eng, esp)
	}
	return date
}

// Obtener el numero de notificaciones no leídas de un cliente.
// Devuelve un entero y un error en caso de que ocurra.
func GetUnreadNotificationsCount(clientID uint) (int, error) {
	var count int64
	err := db.Model(&models.Notifications{}).Where("client_id = ? AND read = ?", clientID, false).Count(&count).Error
	return int(count), err
}

// Marcar todas las notificaciones de un cliente como leídas.
// Devuelve un error en caso de que ocurra.
func MarkAllNotificationsAsRead(clientID uint) error {
	err := db.Model(&models.Notifications{}).Where("client_id = ?", clientID).Update("read", true).Error
	return err
}

// Obtiene las notificaciones de un cliente por su ID.
// Devuelve un slice de models.GroupedNotifications y un error en caso de que ocurra.
func GetNotificationsByClientID(clientID uint) ([]models.GroupedNotifications, error) {
	var notifications []models.Notifications
	err := db.Where("client_id = ?", clientID).Order("date DESC").Find(&notifications).Error
	if err != nil {
		return nil, err
	}

	grouped := make(map[string][]models.NotificationsGet)

	for _, notification := range notifications {
		// Formateamos la fecha en español y en formato de 12 horas
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
			Date:          date,
			Notifications: notifications,
		})
	}

	MarkAllNotificationsAsRead(clientID)
	return groupedNotifications, nil
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
