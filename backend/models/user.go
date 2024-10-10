// Autores:
//   - Carlos Zamudio

package models

// Estructura de la tabla de usuarios
type User struct {
	ID       uint   `json:"-" gorm:"primarykey"`
	Email    string `json:"email" validate:"required,email"`
	Password string `json:"password" validate:"required,min=8"`
}

// Regresa el ID del usuario
func (u *User) GetID() uint {
	return u.ID
}

// Regresa el email del usuario
func (u *User) GetEmail() *string {
	return &u.Email
}

// Regresa el password del usuario
func (u *User) GetPassword() *string {
	return &u.Password
}
