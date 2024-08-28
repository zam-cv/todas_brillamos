package models

import "gorm.io/gorm"

type Card struct {
	gorm.Model
	Quantity  uint
	ProductID uint
	ClientID  uint
}
