/*
* Backend-models: Código que contiene el modelo de carrito y sus atributos
* @author: Min Che Kim
 */
package models

/*
 * Estructura de la tabla Cart
 */
type Cart struct {
	ID        uint `json:"-" gorm:"primarykey"`
	Quantity  uint
	ProductID uint
	ClientID  uint
}
