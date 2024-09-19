package database

import (
	"backend/models"
)

func GetAllUsers() ([]models.User, error) {
	users := []models.User{}
	if err := db.Find(&users).Error; err != nil {
		return nil, err
	}

	return users, nil
}

func GetUserByID(id uint) (*models.User, error) {
	user := &models.User{}
	if err := db.First(user, id).Error; err != nil {
		return nil, err
	}

	return user, nil
}
