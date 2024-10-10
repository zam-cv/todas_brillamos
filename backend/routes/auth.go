// Autores:
//   - Carlos Zamudio

package routes

import (
	"backend/config"
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"time"

	"github.com/gin-gonic/gin"
)

// Implementa la interfaz Auth para la autenticación de administradores.
type AdminAuthService struct{}

// Devuelve la configuración de autenticación para administradores.
func (a *AdminAuthService) GetConfig() *auth.AuthConfig {
	return &auth.AuthConfig{
		SecretKey:     config.AdminSecretKey,
		CookieName:    config.AdminTokenCookieName,
		AuthTypes:     map[auth.AuthType]struct{}{auth.SupportTokenAuth: {}},
		MaxAttempts:   5,
		BlockDuration: 5 * time.Minute,
	}
}

// Obtiene un usuario administrador por su email.
func (a *AdminAuthService) GetUserByEmail(email string) (*models.User, error) {
	user, err := database.GetUserByAdminEmail(email)
	if err != nil {
		return nil, err
	}
	return user, nil
}

// Crea un nuevo usuario administrador.
func (a *AdminAuthService) CreateUser(user *models.User) (uint, error) {
	id, err := database.CreateAdminWithUser(user)
	if err != nil {
		return 0, err
	}

	return id, nil
}

// Obtiene un usuario administrador por su ID.
func (a *AdminAuthService) GetUserById(id int) (*models.User, error) {
	user, err := database.GetUserByAdminID(id)
	if err != nil {
		return nil, err
	}

	return user, nil
}

// Implementa la interfaz Auth para la autenticación de clientes.
type ClientAuthService struct{}

// Devuelve la configuración de autenticación para clientes.
func (a *ClientAuthService) GetConfig() *auth.AuthConfig {
	return &auth.AuthConfig{
		SecretKey:     config.ClientSecretKey,
		CookieName:    config.ClientTokenCookieName,
		AuthTypes:     map[auth.AuthType]struct{}{auth.SupportTokenAuth: {}},
		MaxAttempts:   5,
		BlockDuration: 5 * time.Minute,
	}
}

// Obtiene un usuario cliente por su email.
func (a *ClientAuthService) GetUserByEmail(email string) (*models.User, error) {
	user, err := database.GetUserByClientEmail(email)
	if err != nil {
		return nil, err
	}

	return user, nil
}

// No está implementado para clientes.
func (a *ClientAuthService) CreateUser(user *models.User) (uint, error) {
	// no need to implement
	return 0, nil
}

// Obtiene un usuario cliente por su ID.
func (a *ClientAuthService) GetUserById(id int) (*models.User, error) {
	user, err := database.GetUserByClientID(id)
	if err != nil {
		return nil, err
	}

	return user, nil
}

// Es una instancia de Auth para la autenticación de clientes.
var ClientAuth auth.Auth[*models.User] = &ClientAuthService{}

// Es una instancia de Auth para la autenticación de administradores.
var AdminAuth auth.Auth[*models.User] = &AdminAuthService{}

// Añade las rutas de autenticación para usuarios clientes.
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

// Añade las rutas de autenticación para administradores.
func addAdminAuthRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/auth/admin")

	auth.SigninHandler(AdminAuth, group)
	auth.VerifyHandler(AdminAuth, group)
}
