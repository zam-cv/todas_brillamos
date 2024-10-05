package database

import (
	"backend/models"
	"errors"
)

// CreateCategory crea una nueva categoría en la base de datos.
// Devuelve un error en caso de que ocurra.
func CreateCategory(category *models.Category) (err error) {
	if _, err := GetCategoryByName(category.Name); err == nil {
		return errors.New("category already exists")
	}

	db.Create(category)
	return nil
}

// GetCategoryByName obtiene una categoría por su nombre.
// Devuelve un puntero a models.Category y un error en caso de que ocurra.
func GetCategoryByName(name string) (*models.Category, error) {
	var category models.Category
	err := db.Where("name = ?", name).First(&category).Error
	return &category, err
}

// GetCategoryByID obtiene una categoría por su ID.
// Devuelve un puntero a models.Category y un error en caso de que ocurra.
func GetCategoryByID(id int) (*models.Category, error) {
	var category models.Category
	err := db.First(&category, id).Error
	return &category, err
}

// GetCategories obtiene todas las categorías de la base de datos.
// Devuelve una lista de categorías y un error en caso de que ocurra.
func GetCategories() ([]models.Category, error) {
	var categories []models.Category
	err := db.Find(&categories).Error
	return categories, err
}
