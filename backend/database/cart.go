// Contiene las operaciones relacionadas con el carrito de compras.
// Autores:
//   - Min Che Kim
//   - Carlos Zamudio

package database

import (
	"backend/models"
)

// Agrega un producto al carrito de un cliente.
// Devuelve un error en caso de que ocurra.
func AddProduct(productID, clientID, quantity uint) error {
	cart := &models.Cart{ProductID: productID, ClientID: clientID, Quantity: quantity}
	if err := db.Create(cart).Error; err != nil {
		return err
	}
	return nil
}

// Obtiene el carrito de un cliente por su ID.
// Devuelve una lista de productos en el carrito y un error en caso de que ocurra.
func GetCartByClientID(clientID uint) ([]models.Cart, error) {
	carts := []models.Cart{}
	err := db.Where("client_id = ?", clientID).Find(&carts).Error
	return carts, err
}

// Obtiene todos los productos del carrito de un cliente por su ID.
// Devuelve una lista de productos en el carrito y un error en caso de que ocurra.
func GetAllCartByClientID(clientID uint) ([]models.CartItem, error) {
	var cartItems []struct {
		Quantity  uint
		ProductID uint
		Name      string
		Price     float64
		Hash      string
		Type      string
	}

	err := db.Table("carts").
		Select("carts.quantity, carts.product_id, products.name, products.price, products.hash, products.type").
		Joins("LEFT JOIN products ON carts.product_id = products.id").
		Where("carts.client_id = ?", clientID).
		Scan(&cartItems).Error

	if err != nil {
		return nil, err
	}

	result := make([]models.CartItem, len(cartItems))
	for i, item := range cartItems {
		result[i] = models.CartItem{
			Quantity: item.Quantity,
			Product: models.CartProduct{
				Name:      item.Name,
				Price:     item.Price,
				ProductID: item.ProductID,
				Hash:      item.Hash,
				Type:      item.Type,
			},
		}
	}

	return result, nil
}

// GetProductFromCartByProductIDClientID obtiene un producto del carrito por el ID del producto y el ID del cliente.
// Devuelve un puntero a models.Cart y un error en caso de que ocurra.
func GetProductFromCartByProductIDClientID(productID, clientID uint) (*models.Cart, error) {
	cart := &models.Cart{}
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).First(cart).Error
	return cart, err
}

// UpdateProductQuantity actualiza la cantidad de un producto en el carrito.
// Devuelve un error en caso de que ocurra.
func UpdateProductQuantity(productID, clientID, quantity uint) error {
	cart := &models.Cart{}
	err := db.Model(cart).Where("product_id = ? AND client_id = ?", productID, clientID).Update("quantity", quantity).Error
	return err
}

// DeleteProductFromCart elimina un producto del carrito por el ID del producto y el ID del cliente.
// Devuelve un error en caso de que ocurra.
func DeleteProductFromCart(productID, clientID uint) error {
	err := db.Where("product_id = ? AND client_id = ?", productID, clientID).Delete(&models.Cart{}).Error
	return err
}

// DeleteAllProductsFromCart elimina todos los productos del carrito de un cliente por su ID.
// Devuelve un error en caso de que ocurra.
func DeleteAllProductsFromCart(clientID uint) error {
	err := db.Where("client_id = ?", clientID).Delete(&models.Cart{}).Error
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
