// Autores:
//   - Carlos Zamudio
//   - Mariana Balderrábano

package models

// Estructura de la tabla de clientes
type Client struct {
	ID        uint   `json:"-" gorm:"primarykey"`
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
	UserID    uint
}

// Estructura de la tabla de clientes con datos de usuario
type ClientUser struct {
	Email     string `json:"email" validate:"required,email"`
	Password  string `json:"password" validate:"required,min=8"`
	FirstName string `json:"first_name" validate:"required,min=2"`
	LastName  string `json:"last_name" validate:"required,min=2"`
}

// Estructura de detalles de cliente
type ClientDetails struct {
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name" validate:"required,min=2"`
	Email     string `json:"email" validate:"required,email"`
}

// Regresa el ID del cliente
func (c *ClientUser) GetID() uint {
	// no need to implement this method
	return 0
}

// Regresa el email del cliente
func (c *ClientUser) GetEmail() *string {
	return &c.Email
}

// Regresa el password del cliente
func (c *ClientUser) GetPassword() *string {
	return &c.Password
}
