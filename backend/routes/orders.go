package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/resources/files"
	"backend/resources/auth"

	"github.com/gin-gonic/gin"
)

func addOrdersRoutes(rg *gin.RouterGroup) {

	orders := rg.Group("/orders")

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
}