package models

type Car struct {
	ID        uint `json:"-" gorm:"primarykey"`
	Quantity  uint
	ProductID uint
	ClientID  uint
}
