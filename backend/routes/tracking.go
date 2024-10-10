/*
* Backend-routes: Código que determina los endpoints de tracking y sus rutas
* @author: Jennyfer Jasso
*/
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

/*
* Función para agregar rutas de tracking a la API (Get, Post, Put, Delete)
 */
func addTrackingRoutes(rg *gin.RouterGroup) {
	tracking := rg.Group("/tracking")

	/*
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

	tracking.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		//id, _ := c.MustGet("clientID").(uint)

		c.JSON(200, gin.H{
			"message": "Get all tracking information",
		})
	})

	tracking.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking information",
		})
	})

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

	tracking.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete tracking information",
		})
	})

	tracking.GET("/status", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking status",
		})
	})

	tracking.GET("/location", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get tracking location",
		})
	})
}
