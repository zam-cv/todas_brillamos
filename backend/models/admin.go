package models

<<<<<<< HEAD:backend/models/admin.go
type Admin struct {
	ID     uint `json:"-" gorm:"primarykey"`
	UserID uint
=======
import (
	"errors"
	"regexp"

	"gorm.io/gorm"
)

type Admin struct {
	gorm.Model
	UserID   uint
	Email    string
	Password string
}

func (admin *Admin) Validate() error {
	emailRegex := regexp.MustCompile(`^[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$`)
	if !emailRegex.MatchString(admin.Email) {
		return errors.New("Ingrese un correo válido")
	}
	if len(admin.Password) < 8 {
		return errors.New("La contraseña debe de tener 8 caracteres o más")
	}
	return nil
>>>>>>> a62c38aa4bcb8405ccf67ec866bb7fbbf4d37497:backend/src/models/admin.go
}
