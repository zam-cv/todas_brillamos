// Carga de variables de entorno y definir constantes utilizadas en toda la aplicación backend.
//
// Autores:
//   - Carlos Zamudio

package config

import (
	"os"

	"github.com/joho/godotenv"
)

// Variables globales para almacenar los valores de configuración.
var (
	// Configuración del servidor
	Port string

	// Configuración de la base de datos
	DbHost     string
	DbUser     string
	DbPassword string
	DbName     string

	// Configuración de autenticación
	AdminSecretKey        string
	AdminTokenCookieName  string
	ClientSecretKey       string
	ClientTokenCookieName string
	StripeSecretKey       string
)

// LoadEnvVars carga las variables de entorno desde un archivo .env.
// Entra en pánico si no se puede cargar el archivo .env.
func LoadEnvVars() {
	if err := godotenv.Load(); err != nil {
		panic(err)
	}

	// Configuración del servidor
	Port = os.Getenv("PORT")

	// Configuración de la base de datos
	DbHost = os.Getenv("DB_HOST")
	DbUser = os.Getenv("DB_USER")
	DbPassword = os.Getenv("DB_PASSWORD")
	DbName = os.Getenv("DB_NAME")

	// Configuración de autenticación
	AdminSecretKey = os.Getenv("ADMIN_SECRET_KEY")
	AdminTokenCookieName = os.Getenv("ADMIN_TOKEN_COOKIE_NAME")
	ClientSecretKey = os.Getenv("CLIENT_SECRET_KEY")
	ClientTokenCookieName = os.Getenv("CLIENT_TOKEN_COOKIE_NAME")
	StripeSecretKey = os.Getenv("STRIPE_SECRET_KEY")
}
