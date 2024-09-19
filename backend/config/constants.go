package config

import (
	"os"

	"github.com/joho/godotenv"
)

var (
	// Server
	Port string

	// Database
	DbHost     string
	DbUser     string
	DbPassword string
	DbName     string

	// Auth
	AdminSecretKey        string
	AdminTokenCookieName  string
	ClientSecretKey       string
	ClientTokenCookieName string
)

func LoadEnvVars() {
	if err := godotenv.Load(); err != nil {
		panic(err)
	}

	// Server
	Port = os.Getenv("PORT")

	// Database
	DbHost = os.Getenv("DB_HOST")
	DbUser = os.Getenv("DB_USER")
	DbPassword = os.Getenv("DB_PASSWORD")
	DbName = os.Getenv("DB_NAME")

	// Auth
	AdminSecretKey = os.Getenv("ADMIN_SECRET_KEY")
	AdminTokenCookieName = os.Getenv("ADMIN_TOKEN_COOKIE_NAME")
	ClientSecretKey = os.Getenv("CLIENT_SECRET_KEY")
	ClientTokenCookieName = os.Getenv("CLIENT_TOKEN_COOKIE_NAME")
}
