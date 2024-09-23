package database

import (
	"backend/models"
)

func CreateNotification(notification *models.Notifications) error {
	if err := db.Create(notification).Error; err != nil {
		return err
	}
	return nil
}

func GetNotificationsByClientID(clientID uint) ([]models.Notifications, error) {
	var notifications []models.Notifications
	err := db.Where("client_id = ?", clientID).Find(&notifications).Error
	return notifications, err
}

func DeleteNotificationByID(id uint) error {
	err := db.Where("id = ?", id).Delete(&models.Notifications{}).Error
	return err
}
