// Autores:
//   - Jennyfer Jasso

package models

// Estructura de la tabla de otros (otra información del cliente)
type Other struct {
	ID        uint   `json:"-" gorm:"primarykey"`
	CURP      string `json:"curp" validate:"required,len=18"`
	Street    string `json:"street" validate:"required"`
	Interior  *int   `json:"interior" validate:"omitempty"`
	Exterior  int    `json:"exterior"`
	City      string `json:"city" validate:"required,min=1"`
	State     string `json:"state" validate:"required,min=1"`
	ZIP       string `json:"zip" validate:"required,min=4,max=10"`
	Reference string `json:"reference"`
	ClientID  uint   `json:"-"`
}
