package models

type Orders struct {
	ID           uint `json:"-" gorm:"primarykey"`
	Quantity     uint
	DeliveryDate string
	Status       string
	ProductID    uint
	ClientID     uint
}
