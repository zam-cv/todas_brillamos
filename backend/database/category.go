package database

import (
	"backend/models"
	"errors"
)

func CreateCategory(category *models.Category) (err error) {
	if _, err := GetCategoryByName(category.Name); err == nil {
		return errors.New("category already exists")
	}

	db.Create(category)
	return nil
}

func GetCategoryByName(name string) (*models.Category, error) {
	var category models.Category
	err := db.Where("name = ?", name).First(&category).Error
	return &category, err
}

func GetCategoryByID(id int) (*models.Category, error) {
	var category models.Category
	err := db.First(&category, id).Error
	return &category, err
}

func GetCategories() ([]models.Category, error) {
	var categories []models.Category
	err := db.Find(&categories).Error
	return categories, err
}
