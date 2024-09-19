package models

type Category struct {
	ID   uint   `json:"-" gorm:"primarykey"`
	Name string `json:"name"`
}
