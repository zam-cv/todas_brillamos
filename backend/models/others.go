/*
 * Backend-models: Código que contiene el modelo de Others y sus atributos
 * @author: Jennyfer Jasso
 */

package models

/*
 * Estructura de la tabla Other
 */
type Other struct {
	ID        uint `json:"-" gorm:"primarykey"`
	CURP      string
	Street    string
	Interior  string
	Exterior  string
	City      string
	State     string
	ZIP       string
	Reference string
	ClientID  uint `json:"-"`
}
