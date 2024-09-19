package models

type Card struct {
	ID        uint `json:"-" gorm:"primarykey"`
	Quantity  uint
	ProductID uint
	ClientID  uint
}
