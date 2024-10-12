// Proporciona funciones para la autenticación y manejo de tokens.
// Autores:
//   - Carlos Zamudio
package auth

import (
	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
	"golang.org/x/crypto/bcrypt"
)

// Genera un hash para una contraseña dada.
// Devuelve el hash de la contraseña y un error en caso de que ocurra.
func HashPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), HashingCost)
	return string(bytes), err
}

// Compara una contraseña con su hash.
// Devuelve true si coinciden, de lo contrario false.
func CheckPasswordHash(password, hash string) bool {
	err := bcrypt.CompareHashAndPassword([]byte(hash), []byte(password))
	return err == nil
}

// Genera un token JWT para un ID de usuario dado.
// Devuelve el token y un error en caso de que ocurra.
func GenerateToken(id string, secret string) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"id":  id,
		"exp": TokenExpirationTime,
	})
	return token.SignedString([]byte(secret))
}

// Analiza un token JWT y devuelve el ID del usuario.
// Devuelve el ID del usuario y un error en caso de que ocurra.
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

// Establece una cookie con el token JWT en el contexto de Gin.
func CookieWithToken(c *gin.Context, token string, cookieName string) {
	c.SetCookie(cookieName, token, int(TokenExpirationTime), "/", "", false, true)
}

// Elimina una cookie en el contexto de Gin.
func ClearCookie(c *gin.Context, cookieName string) {
	c.SetCookie(cookieName, "", -1, "/", "", false, true)
}
