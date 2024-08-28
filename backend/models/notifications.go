package models

import "gorm.io/gorm"

type Notifications struct {
	gorm.Model
	Title    string
	Message  string
	ClientID uint
	IsRead   bool
}
