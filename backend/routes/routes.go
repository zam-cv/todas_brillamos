package routes

import (
	"backend/config"
	"time"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

var router = gin.Default()

func Run() {
	router.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"http://localhost:1420"},
		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Origin", "Content-Type", "Accept", "Authorization"},
		AllowCredentials: true,
		ExposeHeaders:    []string{"Content-Length"},
		MaxAge:           12 * time.Hour,
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
