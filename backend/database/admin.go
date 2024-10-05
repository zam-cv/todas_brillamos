/*
* Backend-database: Querys necesarias para la tabla de admin
@author: Carlos Zamudio
*/
package database

import (
	"backend/models"
	"errors"

	"gorm.io/gorm"
)

/*
* Función que obtiene el usuario por su ID de administrador
* @param id: ID del administrador
* @return *models.User: Usuario
* @return error: Error en caso de que exista
 */
func GetUserByAdminID(id int) (*models.User, error) {
	var user models.User
	err := GetDatabase().
		Joins("JOIN admins ON admins.user_id = users.id").
		Where("admins.id = ?", id).
		First(&user).Error
	return &user, err
}

/*
* Función que obtiene el usuario por su email de administrador
* @param email: Email del administrador
* @return *models.User: Usuario
* @return error: Error en caso de que exista
 */
func GetUserByAdminEmail(email string) (*models.User, error) {
	var user models.User
	db := GetDatabase()

	err := db.Joins("JOIN admins ON admins.user_id = users.id").
		Where("users.email = ?", email).
		First(&user).Error

	if err != nil {
		return nil, err
	}

	return &user, nil
}

/*
* Función que crea a un administrador asociado a un usuario
* @param user: Puntero al usuario a asociar
* @return uint: ID del administrador creado
* @return error: Error en caso de que exista
 */
func CreateAdminWithUser(user *models.User) (uint, error) {
	db := GetDatabase()
	tx := db.Begin()

	var existingAdmin models.Admin
	err := tx.Joins("JOIN users ON users.id = admins.user_id").
		Where("users.email = ?", user.Email).
		First(&existingAdmin).Error

	if err == nil {
		tx.Rollback()
		return 0, errors.New("admin already exists")
	} else if err != gorm.ErrRecordNotFound {
		tx.Rollback()
		return 0, err
	}

	if err := tx.Create(user).Error; err != nil {
		tx.Rollback()
		return 0, err
	}

	admin := models.Admin{
		UserID: user.ID,
	}

	if err := tx.Create(&admin).Error; err != nil {
		tx.Rollback()
		return 0, err
	}

	if err := tx.Commit().Error; err != nil {
		return 0, err
	}

	return admin.ID, nil
}
