package models

import "time"

type Donation struct {
	ID          uint      `json:"-" gorm:"primarykey"`
	Amount      uint      `json:"amount"`
	Description string    `json:"description"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"-"`
}
