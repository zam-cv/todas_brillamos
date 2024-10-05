/*
 * Backend-models: Código que contiene el modelo de donación y sus atributos
 * @author: Mariana Balderrábano
 */

package models

import "time"

/*
 * Estructura de la tabla de donaciones
 */
type Donation struct {
	ID          uint      `json:"-" gorm:"primarykey"`
	Amount      uint      `json:"amount"`
	Description string    `json:"description"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"-"`
}

/*
 * Estructura de la tabla de donaciones con datos referentes a la donación hecha
 */
type DonationGet struct {
	ID          uint      `json:"id" gorm:"primarykey"`
	Amount      uint      `json:"amount"`
	Description string    `json:"description"`
	Date        time.Time `json:"-"`
	ClientID    uint      `json:"client_id"`
}
