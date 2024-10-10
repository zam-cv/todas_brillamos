// Definiciones de rutas de chat.
// Autores:
//   - Carlos Zamudio
package routes

import (
	"backend/config"
	"backend/resources/auth"
	"context"
	"fmt"
	"log"
	"strings"
	"time"

	chroma "github.com/amikos-tech/chroma-go"
	"github.com/amikos-tech/chroma-go/collection"
	"github.com/amikos-tech/chroma-go/pkg/embeddings/ollama"
	"github.com/amikos-tech/chroma-go/types"
	"github.com/gin-gonic/gin"
)

// Representa la estructura de un mensaje en el chat.
type Message struct {
	Message string `json:"message"`
}

// Añade las rutas relacionadas con el chat al grupo de rutas proporcionado.
func addChatRoutes(api *gin.RouterGroup) {
	chat := api.Group("/chat")

	var ollamaEf *ollama.OllamaEmbeddingFunction
	var err error

	// Intenta crear una función de embedding Ollama con reintentos.
	createOllamaEF := func() error {
		for attempts := 0; attempts < 5; attempts++ {
			ollamaEf, err = ollama.NewOllamaEmbeddingFunction(
				ollama.WithBaseURL("http://localhost:11434"),
				ollama.WithModel("all-minilm"),
			)
			if err == nil {
				return nil
			}
			log.Printf("Attempt %d: Error creating Ollama embedding function: %s. Retrying in 5 seconds...\n", attempts+1, err)
			time.Sleep(5 * time.Second)
		}
		return fmt.Errorf("failed to create Ollama embedding function after 5 attempts: %w", err)
	}

	if err := createOllamaEF(); err != nil {
		log.Printf("Warning: Could not create Ollama embedding function. Some features may not work: %s\n", err)
	}

	// Inicialización del cliente Chroma
	client, err := chroma.NewClient("http://localhost:8080")
	if err != nil {
		log.Printf("Error creating the client: %s\n", err)
		return
	}

	// Eliminación de la colección existente (si existe)
	_, err = client.DeleteCollection(context.TODO(), "collection")
	if err != nil {
		log.Printf("Error deleting collection (this is normal if it doesn't exist): %s\n", err)
	}

	// Creación de una nueva colección
	newCollection, err := client.NewCollection(
		context.TODO(),
		collection.WithName("collection"),
		collection.WithEmbeddingFunction(ollamaEf),
		collection.WithHNSWDistanceFunction(types.L2),
	)
	if err != nil {
		log.Printf("Error creating the collection: %s\n", err)
		return
	}

	// Creación y configuración del conjunto de registros
	rs, err := types.NewRecordSet(
		types.WithEmbeddingFunction(ollamaEf),
		types.WithIDGenerator(types.NewULIDGenerator()),
	)
	if err != nil {
		log.Printf("Error creating the record set: %s\n", err)
		return
	}

	for _, doc := range config.Documents {
		rs.WithRecord(types.WithDocument(doc))
	}

	_, err = rs.BuildAndValidate(context.TODO())
	if err != nil {
		log.Printf("Error validating the record set: %s\n", err)
		return
	}

	// Adición de registros a la colección
	_, err = newCollection.AddRecords(context.Background(), rs)
	if err != nil {
		log.Printf("Error adding documents: %s\n", err)
		return
	}

	// POST /chat - Procesa un mensaje del chat y devuelve una respuesta
	chat.POST("", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		var message Message
		if err := c.ShouldBindJSON(&message); err != nil {
			c.JSON(400, gin.H{"error": err.Error()})
			return
		}

		qr, qrerr := newCollection.Query(context.TODO(), []string{message.Message}, 1, nil, nil, nil)
		if qrerr != nil {
			c.JSON(500, gin.H{"error": fmt.Sprintf("Error querying documents: %s", qrerr)})
			return
		}

		var response string
		if len(qr.Documents) > 0 && len(qr.Documents[0]) > 0 {
			if len(qr.Documents) > 0 && len(qr.Documents[0]) > 0 {
				response = qr.Documents[0][0]
				if strings.HasPrefix(response, "¿") {
					parts := strings.SplitN(response, "?", 3)
					if len(parts) >= 2 {
						response = strings.TrimSpace(parts[1])
					}
				}
			} else {
				response = "Lo siento, no tengo una respuesta para esa pregunta."
			}
		} else {
			response = "Lo siento, no tengo una respuesta para esa pregunta."
		}

		c.JSON(200, gin.H{
			"message": response,
		})
	})
}
