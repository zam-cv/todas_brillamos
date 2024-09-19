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

func UpdateDataOnOtherByClientID(other *models.Other) error {
	if err := db.Model(&models.Other{}).Where("id = ?", other.ID).Updates(other).Error; err != nil {
		return err
	}

	return nil
}

func GetDataOnOtherByClientID(other *models.Other) error {
	if err := db.Find(&other).Error; err != nil {
		return err
	}

	return nil
}
