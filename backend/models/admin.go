package models

type Admin struct {
	ID     uint `json:"-" gorm:"primarykey"`
	UserID uint
}
