package models

type Other struct {
	ID        uint `json:"-" gorm:"primarykey"`
	CURP      string
	Interior  string
	Exterior  string
	Street    string
	City      string
	ZIP       string
	Reference string
	ClientID  uint
}
