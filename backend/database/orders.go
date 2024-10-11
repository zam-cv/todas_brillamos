// Contiene las operaciones relacionadas con las órdenes de los clientes.
// Autores:
//   - Jennyfer Jasso

package database

import (
	"backend/models"
	"fmt"

	//"fmt"

	"time"

	"gorm.io/gorm"
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

func GetMostCommonProducts() ([]models.MostSellProducts, error) {
	db := GetDatabase()
	var products []models.MostSellProducts

	// Realizamos la consulta que cuenta cuántas veces se ordena cada producto
	err := db.Model(&models.Orders{}).
		Select("products.id, products.name, COUNT(orders.product_id) as order_count").
		Joins("JOIN products ON products.id = orders.product_id").
		Group("products.id, products.name").
		Order("order_count DESC").
		Limit(3).
		Scan(&products).Error

	if err != nil {
		return nil, err
	}

	return products, nil
}

func GetMonthlyRevenue() ([]models.MonthlyRevenue, error) {
	db := GetDatabase()
	var revenue []models.MonthlyRevenue

	// Suponiendo que 'order_received_date' es la fecha de la orden,
	// y que 'quantity' está en la tabla 'orders'
	err := db.Table("orders").
		Select("TO_CHAR(order_received_date, 'YYYY-MM') AS month, SUM(orders.quantity * products.price) AS total_revenue").
		Joins("JOIN products ON products.id = orders.product_id"). // Unir con la tabla de productos
		Group("TO_CHAR(order_received_date, 'YYYY-MM')").
		Order("month").
		Scan(&revenue).Error

	if err != nil {
		return nil, err
	}

	return revenue, nil
}

func GetBestSelledCategories() ([]models.CategorySales, error) {
	db := GetDatabase()
	var categoriesSales []models.CategorySales

	err := db.Table("orders").
		Select("categories.id, categories.name AS category_name, COUNT(orders.id) AS total_sold").
		Joins("LEFT JOIN products ON products.id = orders.product_id").
		Joins("LEFT JOIN categories ON categories.id = products.category_id").
		Where("orders.product_id IS NOT NULL").
		Group("categories.id, categories.name").
		Order("total_sold DESC").
		Limit(5).
		Find(&categoriesSales).Error

	if err != nil {
		return nil, err
	}

	return categoriesSales, nil
}

func GetOrderInfoWithProducts(clientID uint, deliveryDate string) (*models.OrderInfoWithProducts, error) {
	var result models.OrderInfoWithProducts

	// Parsear la fecha de entrega
	parsedDate, err := time.Parse("2006-01-02", deliveryDate)
	if err != nil {
		return nil, fmt.Errorf("error parsing delivery date: %v", err)
	}

	// Obtener información general del pedido (sin el nombre completo)
	err = GetDatabase().
		Table("orders").
		Select("orders.delivery_date, orders.status, orders.order_received_date, orders.preparing_order_date, orders.shipped_date").
		Where("orders.client_id = ? AND DATE(orders.delivery_date) = DATE(?)", clientID, parsedDate).
		First(&result).Error

	if err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, fmt.Errorf("no order found for client ID %d and delivery date %s", clientID, deliveryDate)
		}
		return nil, err
	}

	// Obtener productos asociados al pedido
	var products []models.OrderProduct
	err = GetDatabase().
		Table("orders").
		Select("products.name as product_name, orders.quantity, products.price, products.hash, products.type").
		Joins("JOIN products ON orders.product_id = products.id").
		Where("orders.id = (SELECT id FROM orders WHERE client_id = ? AND DATE(delivery_date) = DATE(?))", clientID, parsedDate).
		Scan(&products).Error

	if err != nil {
		return nil, fmt.Errorf("error fetching products: %v", err)
	}

	// Asignar productos a la estructura
	result.Products = products
	return &result, nil
}

func GetAllOrders(clientID uint) ([]models.OrderSummary, error) {
	var orders []struct {
		DeliveryDate      time.Time
		OrderReceivedDate time.Time
		TotalPrice        float64
		TotalProducts     int
	}

	err := GetDatabase().
		Table("orders").
		Select("delivery_date, delivery_date, SUM(quantity * price) as total_price, SUM(quantity) as total_products").
		Joins("JOIN products ON orders.product_id = products.id").
		Where("client_id = ?", clientID).
		Group("delivery_date, order_received_date").
		Find(&orders).Error

	if err != nil {
		return nil, fmt.Errorf("error fetching orders: %v", err)
	}

	var result []models.OrderSummary

	for _, order := range orders {
		var products []struct {
			Hash string
			Type string
		}

		err := GetDatabase().
			Table("orders").
			Select("products.hash, products.type").
			Joins("JOIN products ON orders.product_id = products.id").
			Where("client_id = ? AND DATE(delivery_date) = DATE(?)", clientID, order.DeliveryDate).
			Scan(&products).Error

		if err != nil {
			return nil, fmt.Errorf("error fetching products for order: %v", err)
		}

		orderSummary := models.OrderSummary{
			TotalPrice:    order.TotalPrice,
			TotalProducts: order.TotalProducts,
			DeliveryDate:  order.DeliveryDate.UTC().Format(time.RFC3339),
			Products:      make([]models.ProductSummary, len(products)),
		}

		for i, p := range products {
			orderSummary.Products[i] = models.ProductSummary{
				Hash: p.Hash,
				Type: p.Type,
			}
		}

		result = append(result, orderSummary)
	}

	return result, nil
}
