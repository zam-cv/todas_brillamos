package models

import (
	"time"
)

type Orders struct {
	ID           uint `json:"-" gorm:"primarykey"`
	Quantity     uint
	DeliveryDate time.Time 
	Status       string `gorm:"type:varchar(20)"`
	OrderReceivedDate	time.Time
	PreparingOrderDate	*time.Time `json:"-"`
	ShippedDate	*time.Time `json:"-"`
	ProductID    uint
	ClientID     uint
}