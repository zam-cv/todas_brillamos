package routes

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

func addCategoriesRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/categories")

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

	group.GET("", func(c *gin.Context) {
		categories, err := database.GetCategories()
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, categories)
	})

}
