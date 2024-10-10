// Definiciones de rutas de carrito.
// Autores:
//   - Min Che Kim
//   - Carlos Zamudio

package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"backend/resources/files"
	"strconv"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con el carrito de compras al grupo de rutas proporcionado.
func addCartRoutes(rg *gin.RouterGroup) {
	cart := rg.Group("/cart")

	// GET /cart - Obtiene todos los items del carrito del cliente
	cart.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		cartItems, err := database.GetAllCartByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		response := models.CartResponse{
			Folder: files.GetURL(ProductArchive),
			Cart:   cartItems,
		}

		c.JSON(200, response)
	})

	// GET /cart/exists/:product_id - Verifica si un producto existe en el carrito del cliente
	cart.GET("/exists/:product_id", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productIDStr := c.Param("product_id")
		productID, err := strconv.Atoi(productIDStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid product ID"})
			return
		}

		_, err = database.GetProductFromCartByProductIDClientID(uint(productID), id)
		if err != nil {
			c.JSON(500, gin.H{"error": "Product not found in cart"})
			return
		}

		c.JSON(200, gin.H{
			"exists": true,
		})
	})

	// POST /cart/:product_id/:quantity - Añade un producto al carrito del cliente
	cart.POST("/:product_id/:quantity", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productValue, _ := c.Get("product")
		product := productValue.(*models.Product)

		quantityStr := c.Param("quantity")

		quantity, err := strconv.Atoi(quantityStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid quantity"})
			return
		}

		err = database.AddProduct(product.ID, uint(id), uint(quantity))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Add item to cart",
		})
	})

	// PUT /cart/:product_id/:quantity - Actualiza la cantidad de un producto en el carrito del cliente
	cart.PUT("/:product_id/:quantity", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productValue, _ := c.Get("product")
		product := productValue.(*models.Product)

		quantityStr := c.Param("quantity")
		quantity, err := strconv.Atoi(quantityStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid quantity"})
			return
		}

		err = database.UpdateProductQuantity(product.ID, id, uint(quantity))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Update cart item",
		})
	})

	// DELETE /cart/:product_id - Elimina un producto del carrito del cliente
	cart.DELETE("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productIDStr := c.Param("product_id")
		productID, err := strconv.Atoi(productIDStr)
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid product ID"})
			return
		}

		err = database.DeleteProductFromCart(uint(productID), uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Delete cart item",
		})
	})
}
