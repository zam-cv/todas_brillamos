// Autores:
//   - Min Che Kim

package models

// Estructura de la tabla de favoritos
type Favorites struct {
	ID        uint `json:"-" gorm:"primarykey"`
	ClientID  uint
	ProductID uint
}
