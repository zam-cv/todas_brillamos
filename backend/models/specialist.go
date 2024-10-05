/*
 * Backend-models: Código que contiene el modelo de especialista
 * @author: Mariana Balderrábano
 */

package models

type Specialist struct {
	ID          uint   `json:"ID" gorm:"primarykey"`
	FirstName   string `json:"first_name" validate:"required,min=2"`
	LastName    string `json:"last_name" validate:"required,min=2"`
	Phone       string `json:"phone" validate:"required,min=10"`
	Speciality  string `json:"speciality" validate:"required,min=2"`
	Description string `json:"description" validate:"required,min=2"`
	ClientID    uint   `json:"-"`
}
