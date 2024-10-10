// Rutas de información adicional de los clientes
// Autores:
//   - Min Che Kim

package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources"
	"backend/resources/auth"

	"github.com/gin-gonic/gin"
)

// Añade las rutas relacionadas con información adicional de los clientes al grupo de rutas proporcionado.
func addOthersRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/others")

	// GET /others/exist - Verifica si existe información adicional para el cliente autenticado
	group.GET("/exist", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)
		exist := database.ExistOthersByClientID(id)
		c.JSON(200, gin.H{"exists": exist})
	})

	// GET /others - Obtiene la información adicional del cliente autenticado
	group.GET("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)
		others, _ := database.GetOthersByClientID(id)
		c.JSON(200, others)
	})

	// POST /others - Crea información adicional para el cliente autenticado
	group.POST("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		id, _ := c.MustGet("clientID").(uint)
		other := &models.Other{}
		if err := c.ShouldBindJSON(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		if err := resources.ValidateStruct(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		other.ClientID = id
		_, err := database.CreateOtherByClientID(other)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.Status(201)
	})

	// PUT /others - Actualiza la información adicional del cliente autenticado
	group.PUT("", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		// actualizar datos adicionales del cliente
		id, _ := c.MustGet("clientID").(uint)
		other := &models.Other{}
		if err := c.ShouldBindJSON(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		if err := resources.ValidateStruct(other); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		other.ClientID = id
		err := database.UpdateOthersByClientID(other)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, other)
	})
}
