/*
 * Backend-routes: Código que determina los endpoints de especialistas y sus métodos
 * @author: Mariana Balderrábano
 */

package routes

import (
	"backend/models"
	"backend/resources/auth"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

var specialistCache []models.Specialist // Variable global para almacenar los especialistas en memoria caché

/*
 * Función para agregar rutas de los especialistas
 */
func addSpecialistsRoutes(api *gin.RouterGroup) {
	specialists := api.Group("/specialists")

	// Endpoint GET que obtiene a todos los especialistas desde la caché
	specialists.GET("", func(c *gin.Context) {
		c.JSON(http.StatusOK, specialistCache)
	})

	// Endpoint POST que agrega a un nuevo especialista a la caché
	specialists.POST("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var newSpecialist models.Specialist
		if err := c.ShouldBindJSON(&newSpecialist); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		newSpecialist.ID = uint(len(specialistCache) + 1) // Genera ID
		specialistCache = append(specialistCache, newSpecialist)
		c.JSON(http.StatusOK, newSpecialist)
	})

	// Endpoint PUT que actualiza datos de un especialista existente con ayuda de su ID
	specialists.PUT("/:id", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		var updatedSpecialist models.Specialist
		if err := c.ShouldBindJSON(&updatedSpecialist); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		// Se asegura que el id del especialista sea el mismo que el que tenía al inicio
		specialistIDStr := c.Param("id")

		specialistID, err := strconv.Atoi(specialistIDStr)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid specialist ID"})
			return
		}

		updatedSpecialist.ID = uint(specialistID)

		// Busca el especialista en la caché y lo actualiza
		for i, specialist := range specialistCache {
			if specialist.ID == uint(specialistID) {
				specialistCache[i] = updatedSpecialist
				c.JSON(http.StatusOK, updatedSpecialist)
				return
			}
		}
		c.JSON(http.StatusNotFound, gin.H{"error": "Specialist not found"})
	})
}
