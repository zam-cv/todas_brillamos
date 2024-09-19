package auth

import (
	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
	"golang.org/x/crypto/bcrypt"
)

func HashPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), HashingCost)
	return string(bytes), err
}

func CheckPasswordHash(password, hash string) bool {
	err := bcrypt.CompareHashAndPassword([]byte(hash), []byte(password))
	return err == nil
}

func GenerateToken(id string, secret string) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"id":  id,
		"exp": TokenExpirationTime,
	})
	return token.SignedString([]byte(secret))
}

func ParseToken(tokenString string, secret string) (string, error) {
	token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		return []byte(secret), nil
	})

	if err != nil {
		return "", err
	}

	claims, ok := token.Claims.(jwt.MapClaims)
	if !ok || !token.Valid {
		return "", err
	}

	id := claims["id"].(string)
	return id, nil
}

func CookieWithToken(c *gin.Context, token string, cookieName string) {
	c.SetCookie(cookieName, token, int(TokenExpirationTime), "/", "", false, true)
}

func ClearCookie(c *gin.Context, cookieName string) {
	c.SetCookie(cookieName, "", -1, "/", "", false, true)
}
