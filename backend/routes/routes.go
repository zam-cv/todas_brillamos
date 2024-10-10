// Rutas de la API
// Autores:
//   - Carlos Zamudio
//   - Min Che Kim
//   - Mariana Balderrábano
//   - Jennyfer Jasso

package routes

import (
	"backend/config"
	"time"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

// Instancia principal del enrutador de Gin.
var router = gin.Default()

// Configura y ejecuta el servidor HTTP.
func Run() {
	// Configuración de CORS
	router.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"http://localhost:1420"},
		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Origin", "Content-Type", "Accept", "Authorization"},
		AllowCredentials: true,
		ExposeHeaders:    []string{"Content-Length"},
		MaxAge:           12 * time.Hour,
	}))

	// Configuración de rutas
	getRoutes()

	// Iniciar el servidor
	router.Run(":" + config.Port)
}

// Configura todas las rutas de la API.
func getRoutes() {
	// Grupo de rutas de la API
	api := router.Group("/api")

	// Añadir rutas específicas
	addUserAuthRoutes(api)
	addAdminAuthRoutes(api)
	addProductRoutes(api)
	addCartRoutes(api)
	addBuyRoutes(api)
	addTrackingRoutes(api)
	addConfigRoutes(api)
	addCategoriesRoutes(api)
	addPostRoutes(api)
	addNotificationsRoutes(api)
	addFavoritesRoutes(api)
	addOthersRoutes(api)
	addOrdersRoutes(api)
	addClientsRoutes(api)
	addDonationsRoutes(api)
	addSpecialistsRoutes(api)
	addChatRoutes(api)
}
