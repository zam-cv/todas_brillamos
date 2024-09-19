package database

import (
	"sync"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var db *gorm.DB
var once sync.Once

func InitDatabase(host, user, password, name string) *gorm.DB {
	once.Do(func() {
		dsn := "host=" + host + " user=" + user + " dbname=" + name + " sslmode=disable password=" + password

		var err error
		db, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
		if err != nil {
			panic(err)
		}
	})

	return db
}

func GetDatabase() *gorm.DB {
	if db == nil {
		panic("Database not initialized")
	}

	return db
}
