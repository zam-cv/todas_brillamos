package resources

import "github.com/go-playground/validator/v10"

var Validate *validator.Validate

func InitValidator() {
	Validate = validator.New()
}

func ValidateStruct(s interface{}) error {
	err := Validate.Struct(s)
	if err != nil {
		return err
	}

	return nil
}
