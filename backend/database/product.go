package database

import "backend/models"

// GetProducts obtiene todos los productos de la base de datos.
// Devuelve una lista de productos y un error en caso de que ocurra.
func GetProducts() ([]models.Product, error) {
	products := []models.Product{}
	if err := db.Find(&products).Error; err != nil {
		return nil, err
	}

	return products, nil
}

// GetProductById obtiene un producto por su ID.
// Devuelve un puntero a models.Product y un error en caso de que ocurra.
func GetProductById(id uint) (*models.Product, error) {
	product := &models.Product{}
	if err := db.First(product, id).Error; err != nil {
		return nil, err
	}

	return product, nil
}

// GetRandomProducts obtiene productos aleatorios que no están en el carrito del cliente.
// Devuelve una lista de productos y un error en caso de que ocurra.
func GetRandomProducts(clientID uint) ([]models.Product, error) {
	products := []models.Product{}
	// Límite de 8 productos que cumplan con esta condición
	// if err := db.Joins("LEFT JOIN carts ON carts.product_id = products.id AND carts.client_id = ?", clientID).Where("carts.id IS NULL").Order("RANDOM()").Limit(8).Find(&products).Error; err != nil {
	// 	return nil, err
	// }
	if err := db.Joins("LEFT JOIN carts ON carts.product_id = products.id AND carts.client_id = ?", clientID).Where("carts.id IS NULL").Order("RANDOM()").Find(&products).Error; err != nil {
		return nil, err
	}
	// if err := db.Order("RANDOM()").Limit(8).Find(&products).Error; err != nil {
	// 	return nil, err
	// }

	return products, nil
}

// RegisterProduct registra un nuevo producto en la base de datos.
// Devuelve el ID del producto creado y un error en caso de que ocurra.
func RegisterProduct(product *models.Product) (uint, error) {
	if err := db.Create(product).Error; err != nil {
		return 0, err
	}

	return product.ID, nil
}

// RemoveProduct elimina un producto por su ID.
// Devuelve el producto eliminado y un error en caso de que ocurra.
func RemoveProduct(id uint) (*models.Product, error) {
	product, err := GetProductById(id)
	if err != nil {
		return nil, err
	}

	if err := db.Delete(product).Error; err != nil {
		return nil, err
	}

	return product, nil
}

// UpdateProduct actualiza un producto por su ID.
// Devuelve un error en caso de que ocurra.
func UpdateProduct(id uint, product *models.Product) error {
	if err := db.Model(&models.Product{}).Where("id = ?", id).Updates(product).Error; err != nil {
		return err
	}

	return nil
}

// GetFileByHashDB obtiene un producto por su hash.
// Devuelve un puntero a models.Product y un error en caso de que ocurra.
func GetFileByHashDB(hash string) (*models.Product, error) {
	product := &models.Product{}
	if err := db.Where("hash = ?", hash).First(product).Error; err != nil {
		return nil, err
	}

	return product, nil
}
