package models

type Post struct {
	ID	uint   `json:"-" gorm:"primarykey"`
	Title string `json:"title"`
	Author string `json:"author"`
	Date string `json:"date"`
	Content string `json:"content"`
}