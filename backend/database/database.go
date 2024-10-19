// Contiene las funciones para inicializar y obtener la conexión a la base de datos.
// Autores:
//   - Carlos Zamudio

package database

import (
	"fmt"
	"log"
	"sync"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var db *gorm.DB
var once sync.Once

// InitDatabase inicializa la conexión a la base de datos.
// Devuelve una instancia de *gorm.DB.
func InitDatabase(host, port, user, password, name string) *gorm.DB {
	once.Do(func() {
		var dsn string
		var err error

		// Compilación condicional
		if isProd() {
			dsn = fmt.Sprintf("host=%s port=%s user=%s password=%s dbname=%s sslmode=require",
				host, port, user, password, name)
			log.Printf("Attempting to connect to database with SSL enabled")
		} else {
			dsn = fmt.Sprintf("host=%s port=%s user=%s password=%s dbname=%s sslmode=disable",
				host, port, user, password, name)
			log.Printf("Attempting to connect to database with SSL disabled")
		}

		db, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
		if err != nil {
			log.Fatalf("Failed to connect to database: %v", err)
		}

		log.Println("Successfully connected to database")
	})

	return db
}

// isProd determina si la aplicación está en modo producción
func isProd() bool {
	return true
}

// Obtiene la instancia de la base de datos.
// Pánico si la base de datos no está inicializada.
func GetDatabase() *gorm.DB {
	if db == nil {
		panic("Database not initialized")
	}

	return db
}
