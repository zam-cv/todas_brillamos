// Rutas relacionadas con el blog.
// Autores:
//   - Carlos Zamudio

package routes

import (
	"github.com/gin-gonic/gin"
)

// addBlogRoutes añade las rutas relacionadas con el blog al grupo de rutas proporcionado.
func addBlogRoutes(rg *gin.RouterGroup) {
	blog := rg.Group("/blog")

	// GET /blog - Obtiene todos los posts del blog
	blog.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get all blog posts",
		})
	})

	// GET /blog/:id - Obtiene un post del blog por su ID
	blog.GET("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Get blog post by id",
		})
	})

	// POST /blog/:id - Crea un nuevo post en el blog
	blog.POST("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Create blog post",
		})
	})

	// PUT /blog/:id - Actualiza un post existente en el blog
	blog.PUT("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Update blog post",
		})
	})

	// DELETE /blog/:id - Elimina un post del blog
	blog.DELETE("/:id", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Delete blog post",
		})
	})
}
