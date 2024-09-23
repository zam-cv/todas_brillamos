package database

import (
	"backend/models"
)

func AddProductToFavorites(productID, clientID uint) error {
	favorite := &models.Favorites{ProductID: productID, ClientID: clientID}
	if err := db.Create(favorite).Error; err != nil {
		return err
	}
	return nil
}

func GetFavoritesByClientID(clientID uint) ([]models.Favorites, error) {
	favorites := []models.Favorites{}
	err := db.Where("client_id = ?", clientID).Find(&favorites).Error
	return favorites, err
}

func DeleteProductFromFavorites(productID, clientID uint) error {
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).Delete(&models.Favorites{}).Error
	return err
}