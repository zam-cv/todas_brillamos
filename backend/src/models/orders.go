package models

import "gorm.io/gorm"

type Orders struct {
	gorm.Model
	Quantity     uint
	DeliveryDate string
	Status       string
	ProductID    uint
	ClientID     uint
}
