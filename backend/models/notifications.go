package models

import "time"

type Notifications struct {
	ID          uint      `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"` // This is the primary key
	Title       string    `json:"title" validate:"required,min=1"`
	Description string    `json:"description" validate:"required,min=1"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"-"`
}
