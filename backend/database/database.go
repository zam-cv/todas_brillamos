// Contiene las funciones para inicializar y obtener la conexión a la base de datos.
// Autores:
//   - Carlos Zamudio

package database

import (
	"sync"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var db *gorm.DB
var once sync.Once

// Inicializa la conexión a la base de datos.
// Devuelve una instancia de *gorm.DB.
func InitDatabase(host, port, user, password, name string) *gorm.DB {
	once.Do(func() {
		dsn := "host=" + host + " port=" + port + " user=" + user + " dbname=" + name + " sslmode=disable password=" + password

		var err error
		db, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
		if err != nil {
			panic(err)
		}
	})

	return db
}

// Obtiene la instancia de la base de datos.
// Pánico si la base de datos no está inicializada.
func GetDatabase() *gorm.DB {
	if db == nil {
		panic("Database not initialized")
	}

	return db
}
