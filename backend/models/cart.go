// Autores:
//   - Min Che Kim

package models

// Estructura de la tabla Cart
type Cart struct {
	ID        uint `json:"-" gorm:"primarykey"`
	Quantity  uint `json:"quantity"`
	ProductID uint `json:"product_id"`
	ClientID  uint `json:"-"`
}

// Estructura de la tabla CartProduct
type CartProduct struct {
	Name      string  `json:"name"`
	Price     float64 `json:"price"`
	ProductID uint    `json:"product_id"`
	Hash      string  `json:"hash"`
	Type      string  `json:"type"`
	Stock     uint    `json:"stock"`
}

// Estructura de la tabla CartItem
type CartItem struct {
	Quantity uint        `json:"quantity"`
	Product  CartProduct `json:"product"`
}

// Estructura de la respuesta del carrito
type CartResponse struct {
	Folder string     `json:"folder"`
	Cart   []CartItem `json:"cart"`
}
