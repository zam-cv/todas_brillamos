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

func addFavoritesRoutes(rg *gin.RouterGroup) {
	favorites := rg.Group("/favorites")

	favorites.GET("/", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		favorites, err := database.GetAllFavoritesByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"folder":    files.GetURL(ProductArchive),
			"favorites": favorites,
		})
	})

	favorites.GET("/exists/:product_id", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productIDStr := c.Param("product_id")
		productID, err := strconv.Atoi(productIDStr)
		if err != nil {
			c.JSON(200, gin.H{
				"exists": false,
			})
			return
		}

		_, err = database.GetProductFromFavoritesByProductIDClientID(uint(productID), id)
		if err != nil {
			c.JSON(200, gin.H{
				"exists": false,
			})
			return
		}

		c.JSON(200, gin.H{
			"exists": true,
		})
	})

	favorites.POST("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productValue, _ := c.Get("product")
		product := productValue.(*models.Product)

		err := database.AddProductToFavorites(product.ID, id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Product added to favorites",
		})
	})

	favorites.DELETE("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productValue, exists := c.Get("product")
		if !exists {
			c.JSON(500, gin.H{"error": "Product not found"})
			return
		}
		product := productValue.(*models.Product)

		err := database.DeleteProductFromFavorites(product.ID, id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Product deleted from favorites",
		})
	})
}
