package main

import (
	"backend/database"
	"backend/routes"

	"backend/config"
	"backend/resources"
)

func main() {
	// Load environment variables
	config.LoadEnvVars()

	// Database setup
	database.InitDatabase(
		config.DbHost,
		config.DbUser,
		config.DbPassword,
		config.DbName,
	)

	// Migrate and create default admin
	config.Migrate()
	config.CreateDefaultAdmin()

	// Create storage folder
	config.CreateStorageFolder()

	// Initialize validator
	resources.InitValidator()

	// Start the server
	routes.Run()
}
