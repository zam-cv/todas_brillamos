// Rutas de posts
// Autores:
//   - Jennyfer Jasso

package routes

import (
	"backend/config"
	"backend/database"
	"backend/models"
	"backend/resources"
	"backend/resources/auth"
	"backend/resources/mail"
	"strconv"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con los posts al grupo de rutas proporcionado.
func addPostRoutes(rg *gin.RouterGroup) {
	post := rg.Group("/posts")

	// POST /posts - Crea un nuevo post (solo para administradores)
	post.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var post models.Post
		if err := c.ShouldBindJSON(&post); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		if err := resources.ValidateStruct(post); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		id, err := database.CreatePost(&post)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		emailsClients := database.GetAllClientsEmails()
		emails := make([]string, len(emailsClients))
		for i, client := range emailsClients {
			emails[i] = client.Email
		}

		mail.NewPost(
			emails,
			post.Title,
			config.ApiKeyMailer,
			config.EmailMailer,
		)

		c.JSON(201, gin.H{"id": id})
	})

	// GET /posts - Obtiene todos los posts
	post.GET("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {

		posts, err := database.GetPosts()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, posts)
	})

	// GET /posts/:id - Obtiene un post específico
	post.GET("/:id", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		idStr := c.Param("id")
		id, err := strconv.Atoi(idStr)
		if err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		post, err := database.GetPostByID(uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, post)

	})

	// DELETE /posts/:id - Elimina un post específico (solo para administradores)
	post.DELETE("/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		idStr := c.Param("id")
		id, err := strconv.Atoi(idStr)
		if err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		post, err := database.DeletePost(uint(id))
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, post)
	})

	post.POST("/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		idStr := c.Param("id")
		id, err := strconv.Atoi(idStr)
		if err != nil {
			c.JSON(400, gin.H{"error": "ID inválido"})
			return
		}

		var post models.Post
		if err := c.ShouldBindJSON(&post); err != nil {
			c.JSON(400, gin.H{"error": "Datos inválidos"})
			return
		}

		err = database.UpdatePost(uint(id), &post)
		if err != nil {
			c.JSON(500, gin.H{"error": "No se pudo actualizar el post"})
			return
		}

		c.JSON(200, gin.H{"message": "Post actualizado"})
	})
}
