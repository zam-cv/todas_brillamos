// Definiciones de rutas de configuración.
// Autores:
//   - Carlos Zamudio
package routes

import (
	"github.com/gin-gonic/gin"
)

// addConfigRoutes añade las rutas relacionadas con la configuración al grupo de rutas proporcionado.
func addConfigRoutes(rg *gin.RouterGroup) {
	config := rg.Group("/config")
	payment := config.Group("/payment")
	notification := config.Group("/notification")

	// GET /config/payment - Obtiene todos los métodos de pago
	payment.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all payment methods",
		})
	})

	// POST /config/payment - Crea un nuevo método de pago
	payment.POST("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Create payment method",
		})
	})

	// DELETE /config/payment/:id - Elimina un método de pago específico
	payment.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete payment method",
		})
	})

	// GET /config/notification - Obtiene todos los tipos de notificaciones
	notification.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all notification types",
		})
	})

	// PUT /config/notification - Actualiza un tipo de notificación
	notification.PUT("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Update notification type",
		})
	})
}
