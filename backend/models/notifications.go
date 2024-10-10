// Autores:
//   - Mariana Balderrábano

package models

import "time"

// Estructura de la tabla de notificaciones
type Notifications struct {
	ID          uint      `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"`
	Title       string    `json:"title" validate:"required,min=1"`
	Description string    `json:"description" validate:"required,min=1"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"-"`
}
