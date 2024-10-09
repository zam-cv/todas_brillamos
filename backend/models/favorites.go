/*
 * Backend-models: Código que contiene el modelo de Favoritos y sus atributos
 * @author: Min Che Kim
 */

package models

/*
 * Estructura de la tabla Favorites
 */
type Favorites struct {
	ID        uint `json:"-" gorm:"primarykey"`
	ClientID  uint
	ProductID uint
}

type FavProduct struct {
	Name      string  `json:"name"`
	Price     float64 `json:"price"`
	ProductID uint    `json:"product_id"`
	Hash      string  `json:"hash"`
	Type      string  `json:"type"`
}