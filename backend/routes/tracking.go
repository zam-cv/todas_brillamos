// Rutas de tracking
// Autores:
//   - Jennyfer Jasso

package routes

import (
	"backend/database"
	"backend/middlewares"
	"time"

	"backend/resources/auth"
	"backend/resources/files"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con el seguimiento de pedidos al grupo de rutas proporcionado.
func addTrackingRoutes(rg *gin.RouterGroup) {
	tracking := rg.Group("/tracking")

	tracking.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		clientID, exists := c.MustGet("clientID").(uint)
		if !exists {
			c.JSON(500, gin.H{"error": "Invalid client ID"})
			return
		}

		result, err := database.GetAllOrders(clientID)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"folder": files.GetURL(ProductArchive),
			"orders": result,
		})
	})

	//GET /tracking/order - Obtiene información de un pedido específico
	tracking.GET("/order/:deliveryDate", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		clientID, exists := c.MustGet("clientID").(uint)
		if !exists {
			c.JSON(500, gin.H{"error": "Invalid client ID"})
			return
		}

		deliveryDateStr := c.Param("deliveryDate")
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

		c.JSON(200, gin.H{
			"folder": files.GetURL(ProductArchive),
			"order":  result,
		})
	})

	// PUT /tracking/:id - Actualiza la información de seguimiento de un pedido (solo para administradores)
	// tracking.PUT("/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
	// 	id := c.Param("id")

	// 	var input struct {
	// 		Status models.Status `json:"status"`
	// 	}

	// 	if err := c.ShouldBindJSON(&input); err != nil {
	// 		c.JSON(400, gin.H{"error": err.Error()})
	// 		return
	// 	}

	// 	orderID, err := strconv.Atoi(id)
	// 	if err != nil {
	// 		c.JSON(400, gin.H{"error": "Invalid order ID"})
	// 		return
	// 	}

	// 	if err := database.UpdateStatusOrders(uint(orderID), input.Status); err != nil {
	// 		c.JSON(500, gin.H{"error": err.Error()})
	// 		return
	// 	}

	// 	c.JSON(200, gin.H{
	// 		"message": "Tracking information updated",
	// 		"status":  input.Status,
	// 	})
	// })
}
