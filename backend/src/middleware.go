package middleware

import (
	"time"

	"github.com/golang-jwt/jwt"
	"github.com/golang-jwt/jwt/v5"
)

var (
	adminSecretKey = []byte("admin_secret") //example not final
)

type Claims struct {
	ID string `json:"id"`
	jwt.StandardClaims
}

func verifyToken(tokenString string, secretKey []byte) (*Claims, error) {
	claims := &Claims{}
	token, err := jwt.ParseWithClaims(tokenString, claims, func(token *jwt.Token) (interface{}, error) {
		return secretKey, nil
	})

	if err != nil || !token.Valid {
		return nil, err
	}

	// Verificar si el token ha expirado
	if claims.ExpiresAt.Time.Before(time.Now()) {
		return nil, jwt.NewValidationError("token expired", jwt.ValidationErrorExpired)
	}

	return claims, nil
}
