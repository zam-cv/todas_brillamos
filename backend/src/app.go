package main

import (
	"log"
	"net/http"
	"sync"
	"sync/atomic"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
	"golang.org/x/crypto/bcrypt"
)

func main() {
	//Inicializar router
	r := gin.Default()

	//Middleware
	r.Use(CORSMiddleware())
	r.Use(LoggerMiddleware())

	var visitorCount uint64

	//HTTP Routes
	r.GET("/", func(c *gin.Context) {
		atomic.AddUint64(&visitorCount, 1)
		c.JSON(http.StatusOK, gin.H{
			"message": "Hello World",
		})
	})

	//Auth routes
	r.POST("/", register)
	r.POST("/", login)

	//Webosocket route
	r.GET("/ws", func(c *gin.Context) {
		handleWebSocket(c.Writer, c.Request)
	})

	//Run HTTP serv
	log.Println("Server running on port 8080")
	if err := r.Run(":8080"); err != nil {
		log.Fatalf("could not run server: %v", err)
	}
}

func CORSMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
		c.Writer.Header().Set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
		c.Writer.Header().Set("Access-Control-Allow-Headers", "Authorization")
		if c.Request.Method == "OPTIONS" {
			c.AbortWithStatus(204)
			return
		}
		c.Next()
	}
}

func LoggerMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		start := time.Now()
		c.Next()
		log.Printf("Request: %s %s took %v", c.Request.Method, c.Request.URL.Path, time.Since(start))
	}
}

// Dummy database using sync.Mutex
var users = struct {
	sync.Mutex
	store map[string]string
}{store: make(map[string]string)}

func hashPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 14)
	return string(bytes), err
}

func verifyPassword(hashedPassword, password string) error {
	return bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(password))
}

func register(c *gin.Context) {
	var json struct {
		Username string `json:"username"`
		Password string `json:"password"`
	}

	if err := c.ShouldBindJSON(&json); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid input"})
		return
	}

	hashedPassword, err := hashPassword(json.Password)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "could not hash password"})
		return
	}

	users.Lock()
	users.store[json.Username] = hashedPassword
	users.Unlock()

	c.JSON(http.StatusOK, gin.H{"message": "User registered"})

}

func login(c *gin.Context) {
	var json struct {
		Username string `json:"username"`
		Password string `json:"password"`
	}

	if err := c.ShouldBindJSON(&json); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid input"})
		return
	}

	// Get user from dummy database
	users.Lock()
	hashedPassword, exists := users.store[json.Username]
	users.Unlock()

	if !exists || verifyPassword(hashedPassword, json.Password) != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid credentials"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Login successful"})
}

// WebSocket handling
var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

func handleWebSocket(w http.ResponseWriter, r *http.Request) {
	conn, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		log.Printf("Failed to set websocket upgrade: %v", err)
		return
	}

	defer conn.Close()
	for {
		_, msg, err := conn.ReadMessage()
		if err != nil {
			log.Printf("Failed to read message: %v", err)
			break
		}
		log.Printf("Message received: %s", msg)

		if err := conn.WriteMessage(websocket.TextMessage, msg); err != nil {
			log.Printf("Failed to write message: %v", err)
			break
		}
	}
}

func startServerWithSSL(router *gin.Engine) {
	server := &http.Server{
		Addr:    ":443",
		Handler: router,
	}

	log.Println("Saerver running on port 443")
	if err := server.ListenAndServeTLS("cert.pem", "key.pem"); err != nil {
		log.Fatalf("could not start server: %v", err)
	}
}
