package routes

import (
	"backend/config"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

var router = gin.Default()

func Run() {
	// CORS
	router.Use(cors.New(cors.Config{
		AllowOrigins: []string{"*"},
		AllowMethods: []string{"GET", "POST", "PUT", "DELETE"},
		AllowHeaders: []string{"Content-Type", "Authorization"},
	}))

	// Get routes
	getRoutes()

	// Run server
	router.Run(":" + config.Port)
}

func getRoutes() {
	// API routes
	api := router.Group("/api")

	// Add routes
	addUserAuthRoutes(api)
	addAdminAuthRoutes(api)
	addProductRoutes(api)
	addCartRoutes(api)
	addBuyRoutes(api)
	addTrackingRoutes(api)
	addConfigRoutes(api)
	addBlogRoutes(api)
	addCategoriesRoutes(api)
	addPostRoutes(api)
	addNotificationsRoutes(api)
	addFavoritesRoutes(api)
	addOthersRoutes(api)
	addOrdersRoutes(api)
	addClientsRoutes(api)
}
