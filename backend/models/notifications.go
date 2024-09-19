package models

<<<<<<< HEAD:backend/models/notifications.go
=======
import (
	"time"

	"gorm.io/gorm"
)

>>>>>>> a62c38aa4bcb8405ccf67ec866bb7fbbf4d37497:backend/src/models/notifications.go
type Notifications struct {
	ID       uint `json:"-" gorm:"primarykey"`
	Title    string
	Message  string
	ClientID uint
	IsRead   bool
	Type     string
	Priority string
	ReadAt   *time.Time
}

// Pendiente de conectar con la base de datos
func createNotification(clientID uint, title, message, notifyType, priority string) error {
	notifications := Notifications{
		Title:    title,
		Message:  message,
		ClientID: clientID,
		Type:     notifyType,
		Priority: priority,
		IsRead:   false,
	}

	// Pendiente añadirlo a la base de datos

	return nil
}

func MarkNotificationAsRead(notificationID uint) error {
	var notification Notifications
	//Queda pendiente implementación con la base de datos
}
