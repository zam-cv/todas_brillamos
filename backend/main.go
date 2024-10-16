package main

import (
	"backend/database"
	"backend/routes"

	"backend/config"
	"backend/resources"

	"github.com/stripe/stripe-go"
)

func main() {
	// Load environment variables
	config.LoadEnvVars()

	// Stripe setup
	stripe.Key = config.StripeSecretKey

	// Database setup
	database.InitDatabase(
		config.DbHost,
		config.DbPort,
		config.DbUser,
		config.DbPassword,
		config.DbName,
	)

	// Migrate
	config.Migrate()

	// Create default admin
	config.CreateDefaultAdmin()

	// Create default categories
	config.CreateDefaultCategories()

	// Create storage folder
	config.CreateStorageFolder()

	// Initialize validator
	resources.InitValidator()

	// Start the server
	routes.Run()
}
