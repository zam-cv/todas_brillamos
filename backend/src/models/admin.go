package models

import "gorm.io/gorm"

type Admin struct {
	gorm.Model
	UserID   uint
	Email    string
	Password string
}
