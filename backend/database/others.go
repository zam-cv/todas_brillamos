// Contiene las operaciones relacionadas con los registros "Other" en la base de datos.
// Autores:
//   - Mariana Balderrábano

package database

import (
	"backend/models"
	"fmt"
)

// Crea un nuevo registro "Other" asociado a un cliente.
// Devuelve el ID del registro creado y un error en caso de que ocurra.
func CreateOtherByClientID(other *models.Other) (uint, error) {
	if err := db.Create(other).Error; err != nil {
		return 0, err
	}
	return other.ID, nil
}

// Obtiene el registro "Other" asociado a un cliente por su ID.
// Devuelve un puntero a models.Other y un error en caso de que ocurra.
func GetOthersByClientID(clientID uint) (*models.Other, error) {
	other := &models.Other{}
	if err := db.Where("client_id = ?", clientID).First(other).Error; err != nil {
		return nil, err
	}
	return other, nil
}

// Verifica si existe un registro "Other" para un cliente específico.
// Devuelve true si existe, false en caso contrario.
func ExistOthersByClientID(clientID uint) bool {
	other := &models.Other{}
	if err := db.Where("client_id = ?", clientID).First(other).Error; err != nil {
		return false
	}

	return true
}

// Actualiza registros "Other" asociados a un cliente por su ID.
// Devuelve un error en caso de que ocurra.
func UpdateOthersByClientID(other *models.Other) error {
	err := db.Model(&models.Other{}).Where("client_id = ?", other.ClientID).Updates(other).Error
	return err
}

func GetAddressByClientID(clientID uint) (string, error) {
	other := &models.Other{}
	if err := db.Where("client_id = ?", clientID).First(other).Error; err != nil {
		return "", err
	}

	address := fmt.Sprintf("Calle %s", other.Street)
	address += fmt.Sprintf(" No. %d", other.Interior)

	if other.Exterior != nil {
		address += fmt.Sprintf("-%d", *other.Exterior)
	}

	address += fmt.Sprintf(" Colonia %s", other.City)
	return address, nil
}
