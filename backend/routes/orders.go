package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/resources/auth"
	"backend/resources/files"
	"strconv"

	"github.com/gin-gonic/gin"
)

func addOrdersRoutes(rg *gin.RouterGroup) {

	orders := rg.Group("/orders")

	orders.GET("/all", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		orders, err := database.GetOrders()

		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"folder": files.GetURL(ProductArchive),
			"orders": orders,
		})
	})

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
}
