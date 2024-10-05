package database

import (
	"backend/models"
)

// CreateOtherByClientID crea un nuevo registro "Other" asociado a un cliente.
// Devuelve el ID del registro creado y un error en caso de que ocurra.
func CreateOtherByClientID(other *models.Other) (uint, error) {
	if err := db.Create(other).Error; err != nil {
		return 0, err
	}

	return other.ID, nil
}

// GetOthersByClientID obtiene registros "Other" asociados a un cliente por su ID.
// Devuelve un puntero a models.Other y un error en caso de que ocurra.
func GetOthersByClientID(clientID uint) (*models.Other, error) {
	others := &models.Other{}
	err := db.Where("client_id = ?", clientID).Find(&others).Error
	return others, err
}

// UpdateOthersByClientID actualiza registros "Other" asociados a un cliente por su ID.
// Devuelve un error en caso de que ocurra.
func UpdateOthersByClientID(other *models.Other) error {
	err := db.Model(&models.Other{}).Where("client_id = ?", other.ClientID).Updates(other).Error
	return err
}