// Autores:
//   - Carlos Zamudio

package models

// Estructura tabla Admin
type Admin struct {
	ID     uint `json:"-" gorm:"primarykey"`
	UserID uint
}
