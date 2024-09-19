package models

type Favorites struct {
	ID        uint `json:"-" gorm:"primarykey"`
	ClientID  uint
	ProductID uint
}
