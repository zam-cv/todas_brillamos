package database

import (
    "backend/models"
)

func GetOrdersByIDandClientID(id int, clientId int) (*models.Orders, error) {
    var orders models.Orders
    err := GetDatabase().
        Joins("JOIN clients ON clients.id = orders.client_id").
        Where("orders.id = ? AND orders.client_id = ?", id, clientId).
        First(&orders).Error
    return &orders, err
}

func CreateOrders(orders []*models.Orders) error {
    db := GetDatabase()

    if err := db.Create(&orders).Error; err != nil {
        return err
    }
    return nil
}

type ProductImage struct {
    Hash string `json:"hash"`
    Type string `json:"type"`
}

type OrderSummary struct {
    DeliveryDate  string         `json:"delivery_date"`
    TotalProducts int            `json:"total_products"`
    TotalAmount   float64        `json:"total_amount"`
    ProductImages []ProductImage `json:"product_images" gorm:"-"`
}

func GetOrdersClientID(clientID uint) ([]OrderSummary, error) {
    var results []OrderSummary

    err := GetDatabase().Raw(`
        SELECT 
            DATE(o.delivery_date) as delivery_date,
            SUM(o.quantity) as total_products,
            SUM(o.quantity * p.price) as total_amount
        FROM orders o
        JOIN products p ON o.product_id = p.id
        WHERE o.client_id = ?
        GROUP BY DATE(o.delivery_date)
        ORDER BY DATE(o.delivery_date)
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
            WHERE o.client_id = ? AND DATE(o.delivery_date) = ?
        `, clientID, results[i].DeliveryDate).Scan(&productImages).Error

        if err != nil {
            return nil, err
        }

        results[i].ProductImages = productImages
	}

	return results, nil
}