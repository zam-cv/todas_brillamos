package database

import (
	"backend/models"
)

func InsertOtherData(other *models.Other) (uint, error) {
	db := GetDatabase()
	tx := db.Begin()
	if err := tx.Create(&other).Error; err != nil {
		tx.Rollback()
		return 0, err
	}

	tx.Commit()
	return other.ID, nil

}
