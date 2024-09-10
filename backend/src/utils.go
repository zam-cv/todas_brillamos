package main

import(
	"crypto/rand"
	"encoding/base64"
	"fmt"
	"log"
	"net/http"
	"os"
	"time"

	"github.com/dgrijalva/jwt-go"
	"github.com/gorilla/securecookie"
	"golang.org/x/crypto/argon2"
	"github.com/rs/cors"
	"github.com/shirou/gopsutil/host"
)

type Claims struct{
	ID int 'json:"id"'
	Exp int64 'json:"exp"'
}


//Hash password util
func HashPassword(password string) (string, error){
	salt := make([]byte, 16)
	
	if _, err := rand.Read(salt); err != nil {
		return "", fmt.Error("falied to generate salt: %v", err)
	}

	hash := argon2.IDKey([]byte(password), salt, 1, 64*1024, 4, 32)
	return base64.RawStdEncoding.EncodeToString(hash),nil
}

// Veryfy password util
func VerifyPassword(password, hash string) bool {
	decodedHash, _ := base64.RawStdEncoding.decodeString(hash)
	newHash := argon2.IDKey([]byte(password), decodedHash[:16],1,64*1024,4,32)
	return string(newHash) == string(decodedHash[:16])
}

func CreateToken(secretKey string, id int) (string, error){
	expirationTime := time.Now().add(5 * time.Minute).Unix()
	claims := &Claims{
		ID: id,
		Exp: expirationTime,
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	tokenString, err := token.SignedString([]byte(secretKey))
	if err != nil {
		return "", fmt.Errorf("failed to create token: %v", err)
	} 
	return tokenString, nil
}

func DecodeToken(secretKey, tokenString string) (*Claims, error){
	claims := &Claims{}
	token, err := jwt.ParseWithClaims(tokenString, claims, func(token *jwt.Token) (interface{}, error){
		return []byte(secretKey), nil
	})
	if err != nil {
		return nil, fmtErrorf("Invalid token %v", err)
	}
	return claims, nil
}

func SetCookieWithToken(w http.ResponderWriter, token string){
	cookie := securecookie.New(nil,nil)
	encoded, _ := cookie.Encode("token", token)	
	http.SetCookie(w, &http.Cookie{
		Name: "token",
		Value: encoded,
		HttpOnly: true,	
		Secure: true,
		Path: "/",
	})
}

func SetCookieWithExpiredToken(w http.ResponderWriter){
	http.SetCookie(w, &http.Cookie{
		Name: "token",
		Value: "",	
		HttpOnly: true,
		Secure: true,
		Path: "/",
		Expires: time.Now().Add(-24 * time.Hour),
	})
}

