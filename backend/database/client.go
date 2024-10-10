// Contiene las operaciones relacionadas con los clientes y usuarios.
// Autores:
//   - Carlos Zamudio
//   - Mariana Balderrábano


package database

import (
	"backend/models"
	"errors"

	"gorm.io/gorm"
)

// Obtiene el usuario por su ID.
// Devuelve un puntero a models.User y un error en caso de que ocurra.
func GetUserByID(id int) (*models.User, error) {
	var user models.User
	err := GetDatabase().First(&user, id).Error
	return &user, err
}

// Actualiza la contraseña del usuario.
// Devuelve un error en caso de que ocurra.
func UpdateUserPassword(id int, password string) error {
	return GetDatabase().Model(&models.User{}).
		Where("id = ?", id).
		Update("password", password).Error
}

// Obtiene el nombre completo por el ID del cliente.
// Devuelve el nombre completo como string y un error en caso de que ocurra.
func GetFullNameByClientID(id int) (string, error) {
	var client models.Client
	err := GetDatabase().First(&client, id).Error
	if err != nil {
		return "", err
	}
	return client.FirstName + " " + client.LastName, nil
}

// Obtiene el cliente por su ID.
// Devuelve un puntero a models.Client y un error en caso de que ocurra.
func GetClientByID(id int) (*models.Client, error) {
	var client models.Client
	err := GetDatabase().First(&client, id).Error
	return &client, err
}

// Obtiene el cliente por el ID del usuario.
// Devuelve un puntero a models.Client y un error en caso de que ocurra.
func GetClientByUserID(id int) (*models.Client, error) {
	var client models.Client
	err := GetDatabase().Where("user_id = ?", id).First(&client).Error
	return &client, err
}

// Obtiene el usuario por el ID del cliente.
// Devuelve un puntero a models.User y un error en caso de que ocurra.
func GetUserByClientID(id int) (*models.User, error) {
	var user models.User
	err := GetDatabase().
		Joins("JOIN clients ON clients.user_id = users.id").
		Where("clients.id = ?", id).
		First(&user).Error
	return &user, err
}


// Obtiene el usuario por el email del cliente.
// Devuelve un puntero a models.User y un error en caso de que ocurra.
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

// Crea un nuevo cliente en la base de datos.
// Devuelve el ID del cliente creado y un error en caso de que ocurra.
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

// Obtiene todos los IDs de los clientes.
// Devuelve un slice de uint con los IDs de los clientes. 
func GetAllClientsIDs() []uint {
	var clients []uint
	GetDatabase().Model(&models.Client{}).Pluck("id", &clients)
	return clients
}

// Obtiene los detalles de un cliente por su ID.
// Devuelve un puntero a models.ClientDetails y un error en caso de que ocurra.
func GetClientDetails(clientID uint) (*models.ClientDetails, error) {
	db := GetDatabase()
	result := models.ClientDetails{}

	err := db.Model(&models.Client{}).
		Select("clients.first_name, clients.last_name").
		Joins("LEFT JOIN users ON users.id = clients.user_id").
		Where("clients.id = ?", clientID).
		First(&result).Error

	if err != nil {
		return nil, err
	}

	return &result, nil
}

// Actualiza los detalles de un cliente.
// Devuelve un error en caso de que ocurra.
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
func GetOthersClientDetails(clientID uint) (*models.ClientOthersInfo, error) {
	db := GetDatabase()
	result := models.ClientOthersInfo{}
	err := db.Model(&models.Client{}).
		Select("clients.first_name, clients.last_name, users.email, others.client_id, others.street, others.curp, others.reference, others.z_ip").
		Joins("LEFT JOIN users ON users.id = clients.user_id").
		Joins("LEFT JOIN others ON others.client_id = clients.id").
		Where("clients.id = ?", clientID).
		First(&result).Error
	if err != nil {
		return nil, err
	}

	return &result, nil
}
