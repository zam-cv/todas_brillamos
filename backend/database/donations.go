/*
 * Backend-database: Querys nececarías para la tabla de donaciones
 * @author: Mariana Balderrábano
 */

package database

import (
	"backend/models"
)

/*
 * Función que crea una nueva donación en la base de datos asociada a un cliente
 * @param donation: Puntero a la donación a crear
 * @return error: Error en caso de que exista
 */
func CreateDonationByClientID(donation *models.Donation) error {
	if err := db.Create(donation).Error; err != nil {
		return err
	}
	return nil
}

/*
 * Función que obtiene todas las donaciones de la base de datos de todos los clientes
 * @return []models.DonationGet: Arreglo de donaciones que contienen datos referentes a la donación hecha
 * @return error: Error en caso de que exista
 */
func GetAllDonations() ([]models.DonationGet, error) {
	var donations []models.Donation
	err := db.Find(&donations).Error
	if err != nil {
		return nil, err
	}

	var donationsResponses []models.DonationGet
	for _, donation := range donations {
		donationsResponses = append(donationsResponses, models.DonationGet{
			ID:          donation.ID,
			Amount:      donation.Amount,
			Description: donation.Description,
			Date:        donation.Date,
			ClientID:    donation.ClientID,
		})
	}
	return donationsResponses, err
}
