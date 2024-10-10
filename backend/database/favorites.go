// Contiene las operaciones relacionadas con los favoritos de los clientes.
// Autores:
//   - Min Che Kim

package database

import (
	"backend/models"
)

// Agrega un producto a los favoritos de un cliente.
// Devuelve un error en caso de que ocurra.
func AddProductToFavorites(productID, clientID uint) error {
	favorite := &models.Favorites{ProductID: productID, ClientID: clientID}
	if err := db.Create(favorite).Error; err != nil {
		return err
	}
	return nil
}

// Obtiene los productos favoritos de un cliente por su ID.
// Devuelve una lista de favoritos y un error en caso de que ocurra.
func GetFavoritesByClientID(clientID uint) ([]models.Favorites, error) {
	favorites := []models.Favorites{}
	err := db.Where("client_id = ?", clientID).Find(&favorites).Error
	return favorites, err
}

// Obtiene un producto favorito por el ID del producto y el ID del cliente.
func GetAllFavoritesByClientID(clientID uint) ([]models.Product, error) {
	var favoriteProducts []models.Product

	result := db.Table("favorites").
		Select("products.*").
		Joins("LEFT JOIN products ON favorites.product_id = products.id").
		Where("favorites.client_id = ?", clientID).
		Scan(&favoriteProducts)

	if result.Error != nil {
		return []models.Product{}, result.Error
	}

	if len(favoriteProducts) == 0 {
		return []models.Product{}, nil
	}

	return favoriteProducts, nil
}

// GetProductFromFavoritesByProductIDClientID obtiene un producto favorito por el ID del producto y el ID del cliente.
// Devuelve un puntero a models.Favorites y un error en caso de que ocurra.
func GetProductFromFavoritesByProductIDClientID(productID, clientID uint) (*models.Favorites, error) {
	favorite := &models.Favorites{}
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).First(favorite).Error
	return favorite, err
}

// Elimina un producto de los favoritos de un cliente por el ID del producto y el ID del cliente.
// Devuelve un error en caso de que ocurra.
func DeleteProductFromFavorites(productID, clientID uint) error {
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).Delete(&models.Favorites{}).Error
	return err
}
