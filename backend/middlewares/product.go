// Contiene los middlewares relacionados con los productos.
// Autores:
//   - Carlos Zamudio

package middlewares

import (
	"backend/database"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

// Es un middleware que verifica si un producto existe en la base de datos.
// Si el producto no existe, responde con un error 404 y aborta la solicitud.
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

// Es un middleware que obtiene el ID del producto de la solicitud.
// Si el ID del producto no está presente o es inválido, responde con un error 401 y aborta la solicitud.
func GetProductID() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		idStr, exists := ctx.Get("productID")
		if !exists {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		id, err := strconv.Atoi(idStr.(string))
		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		product, err := database.GetProductById(uint(id))
		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			ctx.Abort()
			return
		}

		ctx.Set("productID", uint(product.ID))
		ctx.Next()
	}
}
