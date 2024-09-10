package routes

import (
	"github.com/gin-gonic/gin"
)

var router = gin.Default()

func Run() {
	getRoutes()
	router.Run(":8000")
}

func getRoutes() {
	api := router.Group("/api")

	// Sub routes
	addUserAuthRoutes(api)
	addAdminAuthRoutes(api)
	addProductRoutes(api)
	addCartRoutes(api)
	addUserRoutes(api)
	addBuyRoutes(api)
	addTrackingRoutes(api)
	addConfigRoutes(api)
	addBlogRoutes(api)
}
