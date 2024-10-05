/*
* Backend-models: Código que contiene el modelo de Categoría y sus atributos
* @author: Min Che Kim
 */
package models

/*
 * Estructura de la tabla Category
 */
type Category struct {
	ID   uint   `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"`
	Name string `json:"name"`
}
