package auth

import "time"

var (
	// TokenExpirationTime define el tiempo de expiración del token (15 días).
	TokenExpirationTime = time.Now().Add(time.Hour * 24 * 15).Unix() // 15 days
	// HashingCost define el costo de hashing para las contraseñas.
	HashingCost         = 10
)
