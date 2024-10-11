// Autores:
//   - Jennyfer Jasso

package models

import "time"

// Estructura de la tabla de pedidos
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

// Estructura de la tabla de la información de pedido
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

type MonthlyRevenue struct {
	Month        string `json:"month"`
	TotalRevenue uint   `json:"total_revenue"`
}

type CategorySales struct {
	ID           uint   `json:"id"`
	CategoryName string `json:"category_name"`
	TotalSold    uint   `json:"total_sold"`
}

type OrderInfoWithProducts struct {
	DeliveryDate       time.Time      `json:"delivery_date"`
	Status             string         `json:"status"`
	OrderReceivedDate  time.Time      `json:"order_received_date"`
	PreparingOrderDate *time.Time     `json:"preparing_order_date"`
	ShippedDate        *time.Time     `json:"shipped_date"`
	Products           []OrderProduct `json:"products" gorm:"-"`
}

type OrderProduct struct {
	ProductName string  `json:"product_name"`
	Quantity    int     `json:"quantity"`
	Price       float64 `json:"price"`
	Hash        string  `json:"hash"`
	Type        string  `json:"type"`
}

type OrderSummary struct {
	TotalPrice    float64          `json:"total_price"`
	TotalProducts int              `json:"total_products"`
	DeliveryDate  string           `json:"delivery_date"`
	Products      []ProductSummary `json:"products"`
}

type ProductSummary struct {
	Hash string `json:"hash"`
	Type string `json:"type"`
}
