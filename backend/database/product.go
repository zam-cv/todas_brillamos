package database

import "backend/models"

func GetProducts() ([]models.Product, error) {
	products := []models.Product{}
	if err := db.Find(&products).Error; err != nil {
		return nil, err
	}

	return products, nil
}

func GetProductById(id uint) (*models.Product, error) {
	product := &models.Product{}
	if err := db.First(product, id).Error; err != nil {
		return nil, err
	}

	return product, nil
}

func GetRandomProducts() ([]models.Product, error) {
	products := []models.Product{}
	if err := db.Order("RANDOM()").Limit(8).Find(&products).Error; err != nil {
		return nil, err
	}

	return products, nil
}

func RegisterProduct(product *models.Product) (uint, error) {
	if err := db.Create(product).Error; err != nil {
		return 0, err
	}

	return product.ID, nil
}

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

func UpdateProduct(id uint, product *models.Product) error {
	if err := db.Model(&models.Product{}).Where("id = ?", id).Updates(product).Error; err != nil {
		return err
	}

	return nil
}

func GetFileByHashDB(hash string) (*models.Product, error) {
	product := &models.Product{}
	if err := db.Where("hash = ?", hash).First(product).Error; err != nil {
		return nil, err
	}

	return product, nil
}
