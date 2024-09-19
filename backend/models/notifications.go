package models

type Notifications struct {
	ID       uint `json:"-" gorm:"primarykey"`
	Title    string
	Message  string
	ClientID uint
	IsRead   bool
}
