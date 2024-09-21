package database

import (
	"backend/models"
)

func AddProduct(productID, clientID, quantity uint) error {
	cart := &models.Cart{ProductID: productID, ClientID: clientID, Quantity: quantity}
	if err := db.Create(cart).Error; err != nil {
		return err
	}
	return nil
}

func GetCartByClientID(clientID uint) ([]models.Cart, error) {
	cars := []models.Cart{}
	err := db.Where("client_id = ?", clientID).Find(&cars).Error
	return cars, err
}

func DeleteProductFromCart(productID, clientID uint) error {
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).Delete(&models.Cart{}).Error
	return err
}

// func GetCarByCarIDAndProductID(carID, productID uint) (*models.Cart, *models.Product, error) {
// 	car := &models.Cart{}
// 	product := &models.Product{}
// 	err := db.Model(&models.Cart{}).
// 		Joins("JOIN products ON products.id = cars.product_id").
// 		Where("cars.id = ? AND cars.product_id = ?", carID, productID).
// 		First(&car).Error
// 	return car, product, err
// }

