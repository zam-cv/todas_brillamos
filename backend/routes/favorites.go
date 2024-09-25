package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"

	"github.com/gin-gonic/gin"
)

func addFavoritesRoutes(rg *gin.RouterGroup) {
	favorites := rg.Group("/favorites")

	favorites.GET("/", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		favorites, err := database.GetFavoritesByClientID(id)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"favorites": favorites,
		})
	})

	favorites.POST("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)

		productValue, exists := c.Get("product")
		if !exists {
			c.JSON(500, gin.H{"error": "Product not found"})
			return
		}
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
