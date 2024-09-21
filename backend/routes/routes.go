package routes

import (
	"backend/config"

	"github.com/gin-gonic/gin"
)

var router = gin.Default()

func Run() {
	// Get routes
	getRoutes()

	// Run server
	router.Run(":" + config.Port)
}

func getRoutes() {
	// API routes
	api := router.Group("/api")
	addUserAuthRoutes(api)
	addAdminAuthRoutes(api)
	addProductRoutes(api)
	addCartRoutes(api)
	addUserRoutes(api)
	addBuyRoutes(api)
	addTrackingRoutes(api)
	addConfigRoutes(api)
	addBlogRoutes(api)
	addCategoriesRoutes(api)
	addPostRoutes(api)
}
