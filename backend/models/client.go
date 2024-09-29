package models

type Client struct {
	ID        uint   `json:"-" gorm:"primarykey"`
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
	UserID    uint
}

type ClientUser struct {
	Email     string `json:"email" validate:"required,email"`
	Password  string `json:"password" validate:"required,min=8"`
	FirstName string `json:"first_name" validate:"required,min=2"`
	LastName  string `json:"last_name" validate:"required,min=2"`
}

type ClientDetails struct {
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name" validate:"required,min=2"`
	Email     string `json:"email" validate:"required,email"`
}

func (c *ClientUser) GetID() uint {
	// no need to implement this method
	return 0
}

func (c *ClientUser) GetEmail() *string {
	return &c.Email
}

func (c *ClientUser) GetPassword() *string {
	return &c.Password
}
