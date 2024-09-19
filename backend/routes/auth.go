package routes

import (
	"backend/config"
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"time"

	"github.com/gin-gonic/gin"
)

type AdminAuthService struct{}

func (a *AdminAuthService) GetConfig() *auth.AuthConfig {
	return &auth.AuthConfig{
		SecretKey:     config.AdminSecretKey,
		CookieName:    config.AdminTokenCookieName,
		AuthTypes:     map[auth.AuthType]struct{}{auth.SupportTokenAuth: {}},
		MaxAttempts:   5,
		BlockDuration: 5 * time.Minute,
	}
}

func (a *AdminAuthService) GetUserByEmail(email string) (*models.User, error) {
	user, err := database.GetUserByAdminEmail(email)
	if err != nil {
		return nil, err
	}

	return user, nil
}

func (a *AdminAuthService) CreateUser(user *models.User) (uint, error) {
	id, err := database.CreateAdminWithUser(user)
	if err != nil {
		return 0, err
	}

	return id, nil
}

func (a *AdminAuthService) GetUserById(id int) (*models.User, error) {
	user, err := database.GetUserByAdminID(id)
	if err != nil {
		return nil, err
	}

	return user, nil
}

type ClientAuthService struct{}

func (a *ClientAuthService) GetConfig() *auth.AuthConfig {
	return &auth.AuthConfig{
		SecretKey:     config.ClientSecretKey,
		CookieName:    config.ClientTokenCookieName,
		AuthTypes:     map[auth.AuthType]struct{}{auth.SupportTokenAuth: {}},
		MaxAttempts:   5,
		BlockDuration: 5 * time.Minute,
	}
}

func (a *ClientAuthService) GetUserByEmail(email string) (*models.User, error) {
	user, err := database.GetUserByClientEmail(email)
	if err != nil {
		return nil, err
	}

	return user, nil
}

func (a *ClientAuthService) CreateUser(user *models.User) (uint, error) {
	// no need to implement
	return 0, nil
}

func (a *ClientAuthService) GetUserById(id int) (*models.User, error) {
	user, err := database.GetUserByClientID(id)
	if err != nil {
		return nil, err
	}

	return user, nil
}

var ClientAuth auth.Auth[*models.User] = &ClientAuthService{}
var AdminAuth auth.Auth[*models.User] = &AdminAuthService{}

func addUserAuthRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/auth/user")

	auth.SigninHandler(ClientAuth, group)
	auth.Register(ClientAuth, group, database.CreateClient)
	auth.VerifyHandler(ClientAuth, group)

	// auth.POST("/forgot-password", func(c *gin.Context) {
	// 	c.JSON(200, gin.H{
	// 		"message": "Forgot Password",
	// 	})
	// })

	// auth.POST("/reset-password", func(c *gin.Context) {
	// 	c.JSON(200, gin.H{
	// 		"message": "Reset Password",
	// 	})
	// })

	// auth.POST("/change-password", func(c *gin.Context) {
	// 	c.JSON(200, gin.H{
	// 		"message": "Change Password",
	// 	})
	// })
}

func addAdminAuthRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/auth/admin")

	auth.SigninHandler(AdminAuth, group)
	auth.VerifyHandler(AdminAuth, group)
}
