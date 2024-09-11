package main

import (
	"log"
	"net/http"
	"sync/atomic"

	"github.com/gin-gonic/gin"
	ginSwagger "github.com/swaggo/gin-swagger"
	"github.com/swaggo/gin-swagger/swaggerFiles"
	"github.com/swaggo/swag/example/basic/docs"
)

func main() {
	//Inicializar router
	r := gin.Default()

	docs.SwaggerInfo.BasePath = "/"
	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

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
