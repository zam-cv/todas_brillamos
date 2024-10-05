/*
 * Backend-database: Querys necesarias para la tabla de clientes
 * @author: Carlos Zamudio
 * @co-author: Mariana Balderrábano
 */

package database

import (
	"backend/models"
	"errors"

	"gorm.io/gorm"
)

/*
 * Función que obtiene el usuario por su ID
 * @param id: ID del cliente
 * @return string: Usuario
 * @return error: Error en caso de que exista
 */
func GetUserByID(id int) (*models.User, error) {
	var user models.User
	err := GetDatabase().First(&user, id).Error
	return &user, err
}

/*
 * Función que actualiza la contraseña
 * @param id: ID del cliente
 * @param password: Contraseña
 * @return error: Error en caso de que exista
 */
func UpdateUserPassword(id int, password string) error {
	return GetDatabase().Model(&models.User{}).
		Where("id = ?", id).
		Update("password", password).Error
}

/*
 * Función que obtiene el nombre completo por el ID del cliente
 * @param id: ID del cliente
 * @return string: Nombre completo
 * @return error: Error en caso de que exista
 */
func GetFullNameByClientID(id int) (string, error) {
	var client models.Client
	err := GetDatabase().First(&client, id).Error
	if err != nil {
		return "", err
	}
	return client.FirstName + " " + client.LastName, nil
}

/*
 * Función que obtiene el cliente por su ID
 * @param id: ID del cliente
 * @return *models.Client: Cliente
 * @return error: Error en caso de que exista
 */
func GetClientByID(id int) (*models.Client, error) {
	var client models.Client
	err := GetDatabase().First(&client, id).Error
	return &client, err
}

/*
 * Función que obtiene el cliente por el ID del usuario
 * @param id: ID del usuario
 * @return *models.Client: Cliente
 * @return error: Error en caso de que exista
 */
func GetClientByUserID(id int) (*models.Client, error) {
	var client models.Client
	err := GetDatabase().Where("user_id = ?", id).First(&client).Error
	return &client, err
}

/*
 * Función que obtiene el usuario por el ID del cliente
 * @param id: ID del cliente
 * @return *models.User: Usuario
 * @return error: Error en caso de que exista
 */
func GetUserByClientID(id int) (*models.User, error) {
	var user models.User
	err := GetDatabase().
		Joins("JOIN clients ON clients.user_id = users.id").
		Where("clients.id = ?", id).
		First(&user).Error
	return &user, err
}

/*
 * Función que obtiene el usuario por el email del cliente
 * @param email: Email del cliente
 * @return *models.User: Usuario
 * @return error: Error en caso de que exista
 */
func GetUserByClientEmail(email string) (*models.User, error) {
	var user models.User
	db := GetDatabase()

	err := db.Joins("JOIN clients ON clients.user_id = users.id").
		Where("users.email = ?", email).
		First(&user).Error

	if err != nil {
		return nil, err
	}

	return &user, nil
}

/*
 * Función que crea un cliente
 * @param clientUser: Puntero al cliente
 * @return: ID del cliente
 * @return error: Error en caso de que exista
 */
func CreateClient(clientUser *models.ClientUser) (uint, error) {
	db := GetDatabase()
	tx := db.Begin()

	var existingClient models.Client
	err := tx.Joins("JOIN users ON users.id = clients.user_id").
		Where("users.email = ?", clientUser.Email).
		First(&existingClient).Error

	if err == nil {
		tx.Rollback()
		return 0, errors.New("client already exists")
	} else if err != gorm.ErrRecordNotFound {
		tx.Rollback()
		return 0, err
	}

	var user = models.User{
		Email:    clientUser.Email,
		Password: clientUser.Password,
	}
	if err := tx.Create(&user).Error; err != nil {
		tx.Rollback()
		return 0, err
	}

	client := models.Client{
		FirstName: clientUser.FirstName,
		LastName:  clientUser.LastName,
		UserID:    user.ID,
	}
	if err := tx.Create(&client).Error; err != nil {
		tx.Rollback()
		return 0, err
	}

	if err := tx.Commit().Error; err != nil {
		return 0, err
	}

	return client.ID, nil
}

/*
 * Función que obtiene todos los IDs de los clientes
 * @return []uint: Arreglo de IDs de los clientes
 */
func GetAllClientsIDs() []uint {
	var clients []uint
	GetDatabase().Model(&models.Client{}).Pluck("id", &clients)
	return clients
}

/*
 * Función que obtiene los detalles de un cliente
 * @param clientID: ID del cliente
 * @return *models.ClientDetails: Detalles del cliente
 * @return error: Error en caso de que exista
 */
func GetClientDetails(clientID uint) (*models.ClientDetails, error) {
	db := GetDatabase()
	result := models.ClientDetails{}

	err := db.Model(&models.Client{}).
		Select("clients.first_name, clients.last_name, users.email").
		Joins("LEFT JOIN users ON users.id = clients.user_id").
		Where("clients.id = ?", clientID).
		First(&result).Error

	if err != nil {
		return nil, err
	}

	return &result, nil
}

/*
 * Función que actualiza los detalles de un cliente
 * @param clientID: ID del cliente
 * @param details: Puntero a los detalles del cliente
 * @return error: Error en caso de que exista
 */
func UpdateClientDetails(clientID uint, details *models.ClientDetails) error {
	db := GetDatabase()
	tx := db.Begin()

	if err := tx.Model(&models.Client{}).
		Where("id = ?", clientID).
		Updates(map[string]interface{}{
			"first_name": details.FirstName,
			"last_name":  details.LastName,
		}).Error; err != nil {
		tx.Rollback()
		return err
	}

	var userID uint
	if err := tx.Model(&models.Client{}).
		Select("user_id").
		Where("id = ?", clientID).
		First(&userID).Error; err != nil {
		tx.Rollback()
		return err
	}

	if err := tx.Model(&models.User{}).
		Where("id = ?", userID).
		Update("email", details.Email).Error; err != nil {
		tx.Rollback()
		return err
	}

	return tx.Commit().Error
}
