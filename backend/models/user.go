package models

type User struct {
	ID       uint   `json:"-" gorm:"primarykey"`
	Email    string `json:"email" validate:"required,email"`
	Password string `json:"password" validate:"required,min=8"`
}

func (u *User) GetID() uint {
	return u.ID
}

func (u *User) GetEmail() *string {
	return &u.Email
}

func (u *User) GetPassword() *string {
	return &u.Password
}
