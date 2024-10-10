// Contiene las operaciones relacionadas con las donaciones.
// Autores:
//   - Mariana Balderrábano

package database

import (
	"backend/models"
)

// Crea una nueva donación en la base de datos asociada a un cliente.
// Devuelve un error en caso de que ocurra.
func CreateDonationByClientID(donation *models.Donation) error {
	if err := db.Create(donation).Error; err != nil {
		return err
	}
	return nil
}

// Obtiene todas las donaciones de la base de datos de todos los clientes.
// Devuelve un slice de models.DonationGet y un error en caso de que ocurra.
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

func SumAmountOfDonations() (uint, error) {
	var sum uint
	err := db.Model(&models.Donation{}).Select("SUM(amount)").Scan(&sum).Error
	return sum, err
}
