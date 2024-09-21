package middlewares

import (
	"backend/database"
	"net/http"
	"strconv"
	"github.com/gin-gonic/gin"
)

func ExistsProductMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		productID := c.Param("product_id")
		id, err := strconv.Atoi(productID)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid product ID"})
			c.Abort()
			return
		}
		
		product, err := database.GetProductById(uint(id))
		if err != nil {
			c.JSON(http.StatusNotFound, gin.H{"error": "Product not found"})
			c.Abort()
			return
		}

		c.Set("product", product)
		c.Next()
	}
}