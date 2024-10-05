package auth

import (
	"backend/resources"
	"log"
	"net/http"
	"strconv"
	"strings"
	"time"

	"github.com/gin-gonic/gin"
)

// AuthConfig representa la configuración de autenticación.
type AuthConfig struct {
	SecretKey     string
	CookieName    string
	AuthTypes     map[AuthType]struct{}
	MaxAttempts   int
	BlockDuration time.Duration
}

// Credentials representa las credenciales de un usuario.
type Credentials struct {
	Email    string `json:"email" validate:"required,email"`
	Password string `json:"password" validate:"required,min=8"`
}

// User representa la interfaz de un usuario.
type User interface {
	GetID() uint
	GetEmail() *string
	GetPassword() *string
}

// Auth representa la interfaz de autenticación.
type Auth[T User] interface {
	GetConfig() *AuthConfig
	GetUserByEmail(email string) (T, error)
	CreateUser(user T) (uint, error)
	GetUserById(id int) (T, error)
}

// AuthType representa los tipos de autenticación.
type AuthType int

const (
	SupportCookieAuth AuthType = iota
	SupportTokenAuth
)

// AuthMiddleware es un middleware de autenticación que soporta múltiples tipos de autenticación.
func AuthMiddleware(authTypes map[AuthType]struct{}, secret string, cookieName string) gin.HandlerFunc {
	return func(c *gin.Context) {
		var token *string = nil

		// TODO: Limit the number of requests

		if _, ok := authTypes[SupportCookieAuth]; ok {
			cookie, err := c.Cookie(cookieName)
			if err != nil {
				c.Status(http.StatusUnauthorized)
				log.Println(err)
				c.Abort()
				return
			}

			token = &cookie
		} else if _, ok := authTypes[SupportTokenAuth]; ok {
			authHeader := c.GetHeader("Authorization")
			if authHeader == "" {
				c.Status(http.StatusUnauthorized)
				log.Println("Token is required")
				c.Abort()
				return
			}

			tokenString := strings.Split(authHeader, "Bearer ")
			if len(tokenString) != 2 {
				c.Status(http.StatusUnauthorized)
				log.Println("Invalid token")
				c.Abort()
				return
			}

			tokenValue := tokenString[1]
			token = &tokenValue
		}

		if token == nil {
			c.Status(http.StatusUnauthorized)
			log.Println("Token is required")
			c.Abort()
			return
		}

		id, err := ParseToken(*token, secret)
		if err != nil {
			c.Status(http.StatusUnauthorized)
			log.Println(err)
			c.Abort()
			return
		}

		c.Set("userID", id)
		c.Next()
	}
}

// GetMiddleware obtiene el middleware de autenticación configurado.
func GetMiddleware[T User](auth Auth[T]) gin.HandlerFunc {
	config := auth.GetConfig()
	return AuthMiddleware(config.AuthTypes, config.SecretKey, config.CookieName)
}

// SigninHandler maneja la ruta de inicio de sesión.
func SigninHandler[T User](auth Auth[T], group *gin.RouterGroup) {
	config := auth.GetConfig()
	limit, err := NewLimit(config.MaxAttempts, config.BlockDuration)
	if err != nil {
		log.Fatal(err)
		panic(err)
	}

	group.POST("/signin", func(c *gin.Context) {
		var creds Credentials
		if err := c.ShouldBindJSON(&creds); err != nil {
			c.Status(http.StatusBadRequest)
			log.Println(err)
			return
		}

		if err := resources.ValidateStruct(creds); err != nil {
			c.Status(http.StatusBadRequest)
			log.Println(err)
			return
		}

		attempts := GetAttempts(limit, creds.Email)
		if attempts >= limit.MaxAttempts {
			c.JSON(http.StatusTooManyRequests, gin.H{"error": "Too many failed attempts. Try again later."})
			return
		}

		var userToAuth User
		var user, err = auth.GetUserByEmail(creds.Email)
		if err != nil {
			IncrementAttempts(limit, creds.Email)
			c.Status(http.StatusUnauthorized)
			log.Println(err)
			return
		}

		userToAuth = user

		if !CheckPasswordHash(creds.Password, *userToAuth.GetPassword()) {
			IncrementAttempts(limit, creds.Email)
			c.Status(http.StatusUnauthorized)
			log.Println("Invalid credentials")
			return
		}

		ResetAttempts(limit, creds.Email)

		token, err := GenerateToken(strconv.Itoa(int(userToAuth.GetID())), config.SecretKey)
		if err != nil {
			c.Status(http.StatusInternalServerError)
			log.Println(err)
			return
		}

		if _, ok := config.AuthTypes[SupportCookieAuth]; ok {
			CookieWithToken(c, token, config.CookieName)
		}

		c.JSON(http.StatusOK, gin.H{"token": token})
	})
}

// registerUser registra un nuevo usuario.
func registerUser[T User, C User](c *gin.Context, auth Auth[T], createUser func(C) (uint, error)) {
	var user C
	if err := c.ShouldBindJSON(&user); err != nil {
		c.Status(http.StatusBadRequest)
		log.Println(err)
		return
	}

	if err := resources.ValidateStruct(user); err != nil {
		c.Status(http.StatusBadRequest)
		log.Println(err)
		return
	}

	if _, err := auth.GetUserByEmail(*user.GetEmail()); err == nil {
		c.Status(http.StatusConflict)
		log.Println("User already exists")
		return
	}

	hashedPassword, err := HashPassword(*user.GetPassword())
	if err != nil {
		c.Status(http.StatusInternalServerError)
		log.Println(err)
		return
	}

	// TODO: Validate email address

	*user.GetPassword() = hashedPassword
	if _, err := createUser(user); err != nil {
		c.Status(http.StatusInternalServerError)
		log.Println(err)
		return
	}

	c.Status(http.StatusCreated)
}

// Register registra un nuevo usuario con una función de creación personalizada.
func Register[T User, C User](
	auth Auth[T],
	group *gin.RouterGroup,
	createCustomUser func(C) (uint, error),
) {
	group.POST("/register", func(c *gin.Context) {
		registerUser(c, auth, createCustomUser)
	})
}

// RegisterHandler maneja la ruta de registro de usuarios.
func RegisterHandler[T User](auth Auth[T], group *gin.RouterGroup) {
	group.POST("/register", func(c *gin.Context) {
		registerUser(c, auth, auth.CreateUser)
	})
}

// VerifyHandler maneja la ruta de verificación de usuarios.
func VerifyHandler[T User](auth Auth[T], group *gin.RouterGroup) {
	group.GET("/verify", GetMiddleware(auth), func(c *gin.Context) {
		c.Status(http.StatusOK)
	})
}

// SignoutHandler maneja la ruta de cierre de sesión.
func SignoutHandler[T User](auth Auth[T], group *gin.RouterGroup) {
	config := auth.GetConfig()
	group.POST("/signout", GetMiddleware(auth), func(c *gin.Context) {
		if _, ok := config.AuthTypes[SupportCookieAuth]; ok {
			ClearCookie(c, config.CookieName)
		}

		c.Status(http.StatusOK)
	})
}
