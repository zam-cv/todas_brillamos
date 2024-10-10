/*
 * Backend-models: Código que contiene el modelo de notificaciones
 * @author: Mariana Balderrábano
 */

package models

import (
	"time"
)

/*
 * Estructura de la tabla de notificaciones
 */
type Notifications struct {
	ID          uint      `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"`
	Title       string    `json:"title" validate:"required,min=1"`
	Description string    `json:"description" validate:"required,min=1"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"-"`
}

/*
 * Estructura de la información de notificaciones
 */
type NotificationsGet struct {
	Title       string `json:"title" validate:"required,min=1"`
	Description string `json:"description" validate:"required,min=1"`
	ClientID    uint   `json:"-"`
}

/*
 * Estructura de las notificaciones para que sean agrupadas por fecha
 */
type GroupedNotifications struct {
	Date          string             `json:"date" validate:"required,min=1"`
	Notifications []NotificationsGet `json:"notifications" validate:"required,min=1"`
}
