package models

type Other struct {
	ID        uint `json:"-" gorm:"primarykey"`
	CURP      string
	Street    string
	Interior  string
	Exterior  string
	City      string
	State     string
	ZIP       string
	Reference string
	ClientID  uint
}