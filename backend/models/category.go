// Autores:
//   - Min Che Kim

package models

// Estructura de la tabla de categorías
type Category struct {
	ID   uint   `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"`
	Name string `json:"name"`
}
