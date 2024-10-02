package database

import (
	"backend/models"
)

func CreateDonationByClientID(donation *models.Donation) error {
	if err := db.Create(donation).Error; err != nil {
		return err
	}
	return nil
}

func GetAllDonations() ([]models.Donation, error) {
	var donations []models.Donation
	err := db.Find(&donations).Error
	return donations, err
}
