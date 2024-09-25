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

