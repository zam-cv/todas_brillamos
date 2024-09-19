package database

import (
	"backend/models"
)

func GetCarByCarIDAndProductID(carID, productID uint) (*models.Car, *models.Product, error) {
	car := &models.Car{}
	product := &models.Product{}
	err := db.Model(&models.Car{}).
		Joins("JOIN products ON products.id = cars.product_id").
		Where("cars.id = ? AND cars.product_id = ?", carID, productID).
		First(&car).Error
	return car, product, err
}
