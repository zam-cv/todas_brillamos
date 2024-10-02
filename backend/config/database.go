package config

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"os"
)

func Migrate() {
	db := database.GetDatabase()

	// Migrate the schema
	db.AutoMigrate(&models.User{})
	db.AutoMigrate(&models.Client{})
	db.AutoMigrate(&models.Admin{})
	db.AutoMigrate(&models.Category{})
	db.AutoMigrate(&models.Product{})
	db.AutoMigrate(&models.Post{})
	db.AutoMigrate(&models.Cart{})
	db.AutoMigrate(&models.Notifications{})
	db.AutoMigrate(&models.Favorites{})
	db.AutoMigrate(&models.Orders{})
	db.AutoMigrate(&models.Other{})
	db.AutoMigrate(&models.Donation{})
}

func CreateDefaultAdmin() {
	email := os.Getenv("ADMIN_EMAIL")
	password := os.Getenv("ADMIN_PASSWORD")

	hashedPassword, err := auth.HashPassword(password)
	if err != nil {
		panic(err)
	}

	_, err = database.GetUserByAdminEmail(email)
	if err != nil {
		database.CreateAdminWithUser(&models.User{
			Email:    email,
			Password: hashedPassword,
		})
	}
}
