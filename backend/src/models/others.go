package models

import "gorm.io/gorm"

type Other struct {
	gorm.Model
	CardNumber string
	CardType   string
	Expiration string
	CVV        string
	CURP       string
	Interior   string
	Exterior   string
	Street     string
	City       string
	ZIP        string
	Reference  string
	ClientID   uint
}
