package models

import (
	"errors"
	"regexp"

	"gorm.io/gorm"
)

type User struct {
	gorm.Model
	Email    string
	Password string
	Client   Client
	ClientID uint
}

func (user *User) Validate() error {
	emailRegex := regexp.MustCompile(`^[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$`)
	if !emailRegex.MatchString(user.Email) {
		return errors.New("Ingrese un correo válido")
	}
	if len(user.Password) < 8 {
		return errors.New("La contraseña debe de tener 8 caracteres o más")
	}
	return nil
}
