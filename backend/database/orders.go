// Contiene las operaciones relacionadas con las órdenes de los clientes.
// Autores:
//   - Jennyfer Jasso

package database

import (
	"backend/models"
	//"fmt"
	"strings"
	"time"
	//"gorm.io/gorm"
)

// Obtiene todas las órdenes de la base de datos.
// Devuelve una lista de órdenes y un error en caso de que ocurra.
func GetOrders() ([]models.Orders, error) {
	var orders []models.Orders
	err := GetDatabase().Find(&orders).Error
	return orders, err
}

// Obtiene una orden por su ID y el ID del cliente.
// Devuelve un puntero a models.Orders y un error en caso de que ocurra.
func GetOrdersByIDandClientID(id int, clientId int) (*models.Orders, error) {
	var orders models.Orders
	err := GetDatabase().
		Joins("JOIN clients ON clients.id = orders.client_id").
		Where("orders.id = ? AND orders.client_id = ?", id, clientId).
		First(&orders).Error
	return &orders, err
}

// Crea nuevas órdenes en la base de datos.
// Devuelve un error en caso de que ocurra.
func CreateOrders(orders []*models.Orders) error {
	db := GetDatabase()

	for _, order := range orders {
		order.OrderReceivedDate = time.Now().Format("2006-01-02")
		order.DeliveryDate = time.Now().AddDate(0, 0, 5).Format("2006-01-02")
	}

	if err := db.Create(&orders).Error; err != nil {
		return err
	}
	return nil
}

// Representa la estructura de una imagen de producto.
type ProductImage struct {
	Hash string `json:"hash"`
	Type string `json:"type"`
}

// Representa el resumen de una orden.
type OrderSummary struct {
	DeliveryDate  *string        `json:"delivery_date"`
	TotalProducts int            `json:"total_products"`
	TotalAmount   float64        `json:"total_amount"`
	ProductImages []ProductImage `json:"product_images" gorm:"-"`
}

// Obtiene un resumen de órdenes por el ID del cliente.
// Devuelve una lista de resúmenes de órdenes y un error en caso de que ocurra.
func GetOrdersClientID(clientID uint) ([]OrderSummary, error) {
	var results []OrderSummary

	err := GetDatabase().Raw(`
        SELECT 
            o.delivery_date,
            SUM(o.quantity) as total_products,
            SUM(o.quantity * p.price) as total_amount
        FROM orders o
        JOIN products p ON o.product_id = p.id
        WHERE o.client_id = ?
        GROUP BY o.delivery_date
        ORDER BY o.delivery_date
    `, clientID).Scan(&results).Error

	if err != nil {
		return nil, err
	}

	for i := range results {
		var productImages []ProductImage
		err := GetDatabase().Raw(`
            SELECT p.hash, p.type
            FROM orders o
            JOIN products p ON o.product_id = p.id
            WHERE o.client_id = ? AND o.delivery_date = ?
        `, clientID, results[i].DeliveryDate).Scan(&productImages).Error

		if err != nil {
			return nil, err
		}

		deliveryDate := strings.Split(*results[i].DeliveryDate, "T")[0]
		results[i].DeliveryDate = &deliveryDate
		results[i].ProductImages = productImages
	}

	return results, nil
}

// Actualiza el estado de una orden por su ID.
// Devuelve un error en caso de que ocurra.
func UpdateStatusOrders(id uint, status string) error {
	db := GetDatabase()

	var orders models.Orders
	if err := db.Where("id = ?", id).First(&orders).Error; err != nil {
		return err
	}

	orders.Status = status
	now := time.Now().Format("2006-01-02")

	switch status {
	case "Preparando pedido":
		orders.PreparingOrderDate = &now
	case "Enviado":
		orders.ShippedDate = &now
	}

	if err := db.Save(&orders).Error; err != nil {
		return err
	}

	return nil
}

func GetOrderInfo() ([]models.OrderInformation, error) {
	db := GetDatabase()
	var results []models.OrderInformation // Cambia a un slice para almacenar múltiples resultados

	// Realizamos la consulta con Joins para unir las tablas necesarias y hacer el cálculo de total
	err := db.Model(&models.Orders{}).
		Select("orders.id, clients.first_name, clients.last_name, users.email, orders.product_id, orders.client_id, orders.quantity, products.name as product_name, products.price, (orders.quantity * products.price) as total_price").
		Joins("JOIN clients ON clients.id = orders.client_id").
		Joins("JOIN users ON users.id = orders.client_id").
		Joins("JOIN products ON products.id = orders.product_id").
		Find(&results).Error // Cambia de First a Find

	if err != nil {
		return nil, err
	}

	return results, nil // Devuelve el slice de órdenes
}

/*
type OrderProduct struct {
	ProductName string  `json:"product_name"`
	Quantity    int     `json:"quantity"`
	Price       float64 `json:"price"`
}

type OrderInfoWithProducts struct {
	DeliveryDate      time.Time      `json:"delivery_date"`
	Status            string         `json:"status"`
	OrderReceivedDate time.Time      `json:"order_received_date"`
	FullName          string         `json:"full_name"`
	Products          []OrderProduct `json:"products"`
}

func GetOrderInfoWithProducts(clientID uint, deliveryDate string) (*OrderInfoWithProducts, error) {
	var result OrderInfoWithProducts

	// Parsear la fecha de entrega
	parsedDate, err := time.Parse("2006-01-02", deliveryDate)
	if err != nil {
		return nil, fmt.Errorf("error parsing delivery date: %v", err)
	}

	// Obtener información general del pedido (sin el nombre completo)
	err = GetDatabase().
		Table("orders").
		Select("orders.delivery_date, orders.status, orders.order_received_date").
		Where("orders.client_id = ? AND DATE(orders.delivery_date) = DATE(?)", clientID, parsedDate).
		First(&result).Error

	if err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, fmt.Errorf("no order found for client ID %d and delivery date %s", clientID, deliveryDate)
		}
		return nil, err
	}

	// Obtener productos asociados al pedido
	var products []OrderProduct
	err = GetDatabase().
		Table("order_products").
		Select("products.name as product_name, order_products.quantity, products.price").
		Joins("JOIN products ON order_products.product_id = products.id").
		Where("order_products.order_id = (SELECT id FROM orders WHERE client_id = ? AND DATE(delivery_date) = DATE(?))", clientID, parsedDate).
		Scan(&products).Error

	if err != nil {
		return nil, fmt.Errorf("error fetching products: %v", err)
	}

	// Asignar productos a la estructura
	result.Products = products

	return &result, nil
}
*/
