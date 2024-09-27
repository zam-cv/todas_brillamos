package database

import (
	"backend/models"
)

func CreateOtherByClientID(other *models.Other) (uint, error) {
	if err := db.Create(other).Error; err != nil {
		return 0, err
	}

	return other.ID, nil
}

func GetOthersByClientID(clientID uint) (*models.Other, error) {
	others := &models.Other{}
	err := db.Where("client_id = ?", clientID).Find(&others).Error
	return others, err
}

func UpdateOthersByClientID(other *models.Other) error {
	err := db.Model(&models.Other{}).Where("client_id = ?", other.ClientID).Updates(other).Error
	return err
}