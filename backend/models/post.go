package models

type Post struct {
	ID	uint   `json:"-" gorm:"primarykey"`
	Title string `json:"title" validate:"required,min=1"`
	Author string `json:"author" validate:"required,min=1"`
	Date string `json:"date" validate:"required,min=1"`
	Content string `json:"content" validate:"required,min=1"`
}