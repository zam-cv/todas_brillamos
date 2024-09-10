package routes

import (
	"github.com/gin-gonic/gin"
)

func addUserAuthRoutes(rg *gin.RouterGroup) {
	auth := rg.Group("/auth/user")

	auth.POST("/login", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Login",
		})
	})

	auth.POST("/register", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Register",
		})
	})

	auth.GET("/verify", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Verify",
		})
	})

	auth.POST("/forgot-password", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Forgot Password",
		})
	})

	auth.POST("/reset-password", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Reset Password",
		})
	})

	auth.POST("/change-password", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Change Password",
		})
	})

	auth.DELETE("/logout", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Logout",
		})
	})
}

func addAdminAuthRoutes(rg *gin.RouterGroup) {
	auth := rg.Group("/auth/admin")

	auth.POST("/login", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Login",
		})
	})

	auth.POST("/register", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Register",
		})
	})

	auth.GET("/verify", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Verify",
		})
	})

	auth.DELETE("/logout", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Logout",
		})
	})
}
