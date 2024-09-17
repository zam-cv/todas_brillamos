package models

import (
	"errors"
	"regexp"

	"gorm.io/gorm"
)

type Client struct {
	gorm.Model
	FirstName string
	LastName  string
	UserID    uint
}

// Función para validar nombre y apellido
func ValidateName(FirstName string) error {
	// Expresión regular para permitir solo letras y algunos caracteres especiales comunes
	nameRegex := regexp.MustCompile(`^[a-zA-ZÀ-ÿ\s]{2,50}$`)

	// Verificar longitud del nombre
	if len(FirstName) < 2 || len(FirstName) > 50 {
		return errors.New("Nombre inválido")
	}

	// Verificar que el nombre cumpla con la expresión regular
	if !nameRegex.MatchString(FirstName) {
		return errors.New("Nombre inválido")
	}

	return nil
}

func ValidateLastName(LastName string) error {
	lastNameRegex := regexp.MustCompile(`^[a-zA-ZÀ-ÿ\s]{2,50}$`)
	if len(LastName) < 2 || len(LastName) > 50 {
		return errors.New("Nombre inválido")
	}
	if !lastNameRegex.MatchString(LastName) {
		return errors.New("Nombre inválido")
	}
	return nil
}
