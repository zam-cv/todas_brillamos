// Autores:
//   - Carlos Zamudio

package resources

import "github.com/go-playground/validator/v10"

// Es una instancia del validador de estructuras.
var Validate *validator.Validate

// Inicializa el validador de estructuras.
func InitValidator() {
	Validate = validator.New()
}

// Valida una estructura dada.
// Devuelve un error en caso de que la estructura no sea válida.
func ValidateStruct(s interface{}) error {
	err := Validate.Struct(s)
	if err != nil {
		return err
	}

	return nil
}
