// Configuración de autenticación.
// Autores:
//   - Carlos Zamudio

package auth

import "time"

var (
	// Define el tiempo de expiración del token (15 días).
	TokenExpirationTime = time.Now().Add(time.Hour * 24 * 15).Unix() // 15 days
	// Define el costo de hashing para las contraseñas.
	HashingCost         = 10
)