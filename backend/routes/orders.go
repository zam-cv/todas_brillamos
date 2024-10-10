// Rutas de órdenes
// Autores:
//   - Jennyfer Jasso
//   - Carlos Zamudio

package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/resources/auth"
	"backend/resources/files"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con las órdenes al grupo de rutas proporcionado.
func addOrdersRoutes(rg *gin.RouterGroup) {
	orders := rg.Group("/orders")

	// GET /orders/all - Obtiene todas las órdenes (solo para administradores)
	orders.GET("/all", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		orders, err := database.GetOrders()

		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"orders": orders,
		})
	})

	// GET /orders - Obtiene las órdenes del cliente autenticado
	orders.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		orders, err := database.GetOrdersClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"folder": files.GetURL(ProductArchive),
			"orders": orders,
		})
	})

	// PUT /orders/:id - Actualiza el estado de una orden específica (solo para administradores)
	orders.PUT("/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		//id, _ := c.MustGet("clientID").(uint)
		orderID, err := strconv.ParseUint(c.Param("id"), 10, 32)
		if err != nil {
			c.JSON(400, gin.H{"error": "Invalid order ID"})
			return
		}

		var input struct {
			Status string `json:"status"`
		}

		if err := c.ShouldBindJSON(&input); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		err = database.UpdateStatusOrders(uint(orderID), input.Status)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Order status updated",
		})
	})

	// GET /orders/info - Obtiene información general de las órdenes (solo para administradores)
	orders.GET("/info", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		ordersInfo, err := database.GetOrderInfo()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, ordersInfo)
	})

	orders.GET("/BestSell", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		bestSell, err := database.GetMostCommonProducts()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, bestSell)
	})

	orders.GET("/monthRev", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		revenue, err := database.GetMonthlyRevenue()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, revenue)
	})

	orders.GET("/BestSellCategory", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		bestSellCat, err := database.GetBestSelledCategories()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, bestSellCat)
	})
}
