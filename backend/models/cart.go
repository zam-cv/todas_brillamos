package models

type Cart struct {
	ID        uint `json:"-" gorm:"primarykey"`
	Quantity  uint
	ProductID uint
	ClientID  uint
}
