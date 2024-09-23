package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/resources/auth"
	"backend/models"
	"strconv"

	"github.com/gin-gonic/gin"
)

func addFavoritesRoutes(rg *gin.RouterGroup) {
	favorites := rg.Group("/favorites")

	favorites.GET("/", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		id, exists := c.Get("userID")
		if !exists {
			c.JSON(500, gin.H{"error": "User ID not found"})
			return
		}

		favorites, err := database.GetFavoritesByClientID(id.(uint))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"favorites": favorites,
		})
	})

	favorites.POST("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), func(c *gin.Context) {
		idStr, exists := c.Get("userID")
		if !exists {
			c.JSON(500, gin.H{"error": "User ID not found"})
			return
		}

		id, err := strconv.Atoi(idStr.(string))
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid user ID"})
			return
		}

		productValue, exists := c.Get("product")
		if !exists {
			c.JSON(500, gin.H{"error": "Product not found"})
			return
		}
		product := productValue.(*models.Product)

		err = database.AddProductToFavorites(product.ID, uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Product added to favorites",
		})
	})

	favorites.DELETE("/:product_id", auth.GetMiddleware(ClientAuth), middlewares.ExistsProductMiddleware(), func(c *gin.Context) {
		idStr, exists := c.Get("userID")
		if !exists {
			c.JSON(500, gin.H{"error": "User ID not found"})
			return
		}

		id, err := strconv.Atoi(idStr.(string))
		if err != nil {
			c.JSON(500, gin.H{"error": "Invalid user ID"})
			return
		}

		productValue, exists := c.Get("product")
		if !exists {
			c.JSON(500, gin.H{"error": "Product not found"})
			return
		}
		product := productValue.(*models.Product)

		err = database.DeleteProductFromFavorites(product.ID, uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"message": "Product deleted from favorites",
		})
	})
}