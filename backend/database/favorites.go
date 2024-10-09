package database

import (
	"backend/models"
)

// AddProductToFavorites agrega un producto a los favoritos de un cliente.
// Devuelve un error en caso de que ocurra.
func AddProductToFavorites(productID, clientID uint) error {
	favorite := &models.Favorites{ProductID: productID, ClientID: clientID}
	if err := db.Create(favorite).Error; err != nil {
		return err
	}
	return nil
}

// GetFavoritesByClientID obtiene los productos favoritos de un cliente por su ID.
// Devuelve una lista de favoritos y un error en caso de que ocurra.
func GetFavoritesByClientID(clientID uint) ([]models.Favorites, error) {
	favorites := []models.Favorites{}
	err := db.Where("client_id = ?", clientID).Find(&favorites).Error
	return favorites, err
}

func GetAllFavoritesByClientID(clientID uint) ([]models.FavProduct, error) {
	var favItems []models.FavProduct

	err := db.Table("favorites").
		Select("favorites.product_id, products.name, products.price, products.hash, products.type").
		Joins("LEFT JOIN products ON favorites.product_id = products.id").
		Where("favorites.client_id = ?", clientID).
		Scan(&favItems).Error

	if err != nil {
		return nil, err
	}

	return favItems, nil
}

// GetProductFromFavoritesByProductIDClientID obtiene un producto favorito por el ID del producto y el ID del cliente.
// Devuelve un puntero a models.Favorites y un error en caso de que ocurra.
func GetProductFromFavoritesByProductIDClientID(productID, clientID uint) (*models.Favorites, error) {
	favorite := &models.Favorites{}
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).First(favorite).Error
	return favorite, err
}

// DeleteProductFromFavorites elimina un producto de los favoritos de un cliente por el ID del producto y el ID del cliente.
// Devuelve un error en caso de que ocurra.
func DeleteProductFromFavorites(productID, clientID uint) error {
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).Delete(&models.Favorites{}).Error
	return err
}
