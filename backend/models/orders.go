/*
* Backend-models: Código que contiene el modelo de Orders y sus atributos
* @author: Jennyfer Jasso
 */
package models

/*
 * Estructura de la tabla Orders
 */
type Orders struct {
	ID                 uint `json:"-" gorm:"primarykey"`
	Quantity           uint
	DeliveryDate       string  `gorm:"type:date"`
	Status             string  `gorm:"type:varchar(20)"`
	OrderReceivedDate  string  `gorm:"type:date"`
	PreparingOrderDate *string `json:"-" gorm:"type:date"`
	ShippedDate        *string `json:"-" gorm:"type:date"`
	ProductID          uint
	ClientID           uint
}

type OrderInformation struct {
	ID          uint   `json:"id"`
	FirstName   string `json:"first_name"`
	LastName    string `json:"last_name"`
	Email       string `json:"email"`
	Price       uint   `json:"price"`
	Quantity    uint   `json:"quantity"`
	ProductName string `json:"product_name"`
	ProductID   uint   `json:"product_id"`
	ClientID    uint   `json:"client_id"`
	TotalPrice  uint   `json:"total_price"`
}
