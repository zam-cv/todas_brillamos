package models

import (
	"time"

	"gorm.io/gorm"
)

type Notifications struct {
	gorm.Model
	Title    string
	Message  string
	ClientID uint
	IsRead   bool
	Type     string
	Priority string
	ReadAt   *time.Time
}

// Pendiente de conectar con la base de datos
