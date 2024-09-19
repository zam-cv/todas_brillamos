package auth

import "time"

var (
	TokenExpirationTime = time.Now().Add(time.Hour * 24 * 15).Unix() // 15 days
	HashingCost         = 10
)
