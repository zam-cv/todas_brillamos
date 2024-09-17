package models

import (
	"gorm.io/gorm"
)

type Client struct {
	gorm.Model
	FirstName string
	LastName  string
	UserID    uint
}


func Validatre