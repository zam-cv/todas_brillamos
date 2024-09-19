package models

type Other struct {
	ID         uint `json:"-" gorm:"primarykey"`
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
