package models

import "gorm.io/gorm"

type Favorites struct {
	gorm.Model
	ClientID  uint
	ProductID uint
}
