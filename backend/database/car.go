package database

import (
	"backend/models"
)

func GetCarAndProductByClientID(id int) ([]*models.Car, []*models.Product ,error) {	
	var items ([]*models.Car, []*models.Product)
	err := GetDatabase().
		Joins("JOIN products ON products.id = cars.product_id").
		Where("cars.client_id = ?", id).
		First(&user).Error
	return &car, &product.Name, err
}
