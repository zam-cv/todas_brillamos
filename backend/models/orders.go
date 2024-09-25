package models

import "time"

type Orders struct {
	ID           uint `json:"-" gorm:"primarykey"`
	Quantity     uint
	DeliveryDate time.Time 
	Status       string
	ProductID    uint
	ClientID     uint
}
