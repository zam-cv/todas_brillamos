package resources

import "github.com/go-playground/validator/v10"

// Validate es una instancia del validador de estructuras.
var Validate *validator.Validate

// InitValidator inicializa el validador de estructuras.
func InitValidator() {
	Validate = validator.New()
}

// ValidateStruct valida una estructura dada.
// Devuelve un error en caso de que la estructura no sea válida.
func ValidateStruct(s interface{}) error {
	err := Validate.Struct(s)
	if err != nil {
		return err
	}

	return nil
}
