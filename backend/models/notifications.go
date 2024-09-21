package models

import "time"

type Notifications struct {
	ID          uint      `json:"-" gorm:"primarykey"`
	Title       string    `json:"title" validate:"required,min=1"`
	Description string    `json:"description" validate:"required,min=1"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"-"`
}
