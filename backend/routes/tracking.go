// Rutas de tracking
// Autores:
//   - Jennyfer Jasso

package routes

import (
	//"backend/database"
	"backend/middlewares"

	//"backend/models"
	"backend/resources/auth"
	//"log"
	//"time"

	//"backend/models"
	//"backend/database"
	//"strconv"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con el seguimiento de pedidos al grupo de rutas proporcionado.
func addTrackingRoutes(rg *gin.RouterGroup) {
	tracking := rg.Group("/tracking")

	/*
		//GET /tracking/order - Obtiene información de un pedido específico
		tracking.GET("/order", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
			clientID, exists := c.MustGet("clientID").(uint)
			if !exists {
				c.JSON(500, gin.H{"error": "Invalid client ID"})
				return
			}

			deliveryDateStr := c.Query("deliveryDate")
			if deliveryDateStr == "" {
				c.JSON(400, gin.H{"error": "Invalid delivery date format"})
				return
			}

			_, err := time.Parse("2006-01-02", deliveryDateStr)
			if err != nil {
				c.JSON(400, gin.H{"error": "Invalid delivery date format"})
				return
			}

			result, err := database.GetOrderInfoWithProducts(clientID, deliveryDateStr)
			if err != nil {
				c.JSON(500, gin.H{"error": err.Error()})
				return
			}

			//log.Println("Parsed delivery date:", deliveryDateStr)
			//log.Println("Database delivery date:", result.DeliveryDate)

			c.JSON(200, result)
		})
	*/

	// GET /tracking - Obtiene toda la información de seguimiento para el cliente autenticado
	tracking.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		//id, _ := c.MustGet("clientID").(uint)

		c.JSON(200, gin.H{
			"message": "Get all tracking information",
		})
	})

	// GET /tracking/:id - Obtiene información de seguimiento para un pedido específico
	tracking.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking information",
		})
	})

	// PUT /tracking/:id - Actualiza la información de seguimiento de un pedido (solo para administradores)
	tracking.PUT("/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		/*
			id := c.Param("id")

			var input struct {
				Status models.Status `json:"status"`
			}

			if err := c.ShouldBindJSON(&input); err != nil {
				c.JSON(400, gin.H{"error": err.Error()})
				return
			}

			orderID, err := strconv.Atoi(id)
			if err != nil {
				c.JSON(400, gin.H{"error": "Invalid order ID"})
				return
			}

			if err := database.UpdateStatusOrders(uint(orderID), input.Status); err != nil {
				c.JSON(500, gin.H{"error": err.Error()})
				return
			}

			c.JSON(200, gin.H{
				"message": "Tracking information updated",
				"status":  input.Status,
			})
		*/
	})

	// DELETE /tracking/:id - Elimina la información de seguimiento de un pedido
	tracking.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete tracking information",
		})
	})

	// GET /tracking/status - Obtiene el estado de seguimiento
	tracking.GET("/status", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking status",
		})
	})

	// GET /tracking/location - Obtiene la ubicación de seguimiento
	tracking.GET("/location", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking location",
		})
	})
}
