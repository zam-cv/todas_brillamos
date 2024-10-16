package routes

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"net/http"

	"github.com/gin-gonic/gin"
)

func addDonationsRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/donations")

	group.GET("", auth.GetMiddleware(AdminAuth), func(c *gin.Context) {
		donations, err := database.GetDonations()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		if donations == nil {
			c.JSON(http.StatusOK, []models.DonationInfo{})
			return
		}

		c.JSON(http.StatusOK, donations)
	})
}
