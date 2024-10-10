// Definiciones de rutas de categorías.
// Autores:
//   - Min Che Kim

package routes

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con las categorías al grupo de rutas proporcionado.
func addCategoriesRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/categories")

	// POST /categories - Crea una nueva categoría (solo para administradores)
	group.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var category models.Category
		if err := c.ShouldBindJSON(&category); err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		err := database.CreateCategory(&category)
		if err != nil {
			c.Status(http.StatusInternalServerError)
			return
		}
		c.JSON(http.StatusCreated, category)
	})

	// GET /categories/:id - Obtiene una categoría específica por su ID
	group.GET("/:id", func(c *gin.Context) {
		idStr := c.Param("id")
		id, err := strconv.Atoi(idStr)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": "Invalid ID"})
			return
		}

		category, err := database.GetCategoryByID(id)
		if err != nil {
			c.JSON(http.StatusNotFound, gin.H{"error": "Category not found"})
			return
		}

		c.JSON(http.StatusOK, category)
	})

	// GET /categories - Obtiene todas las categorías
	group.GET("", func(c *gin.Context) {
		categories, err := database.GetCategories()
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, categories)
	})
}
