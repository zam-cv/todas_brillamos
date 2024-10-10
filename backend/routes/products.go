// Rutas de productos
// Autores:
//   - Carlos Zamudio
//   - Min Che Kim
package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"backend/resources/files"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

// Implementa la interfaz files.Files para manejar productos.
type Product struct{}

// Devuelve la configuración de archivos para productos.
func (i *Product) GetConfig() *files.FileConfig {
	return &files.FileConfig{
		Folder:       "products",
		OptionalFile: false,
		AllowedTypes: map[files.FileType]struct{}{
			files.JPG:  {},
			files.JPEG: {},
			files.PNG:  {},
		},
		FileSizeLimit: 1000 * 1024, // 1 MB
	}
}

// Crea una nueva instancia de Product.
func (i *Product) NewFile() *models.Product {
	return &models.Product{}
}

// Valida los metadatos del producto.
func (i *Product) ProcessMetadata(metadata *models.ProductMetadata) error {
	if _, err := database.GetCategoryByID(int(metadata.CategoryID)); err != nil {
		return err
	}
	return nil
}

// Registra un nuevo producto en la base de datos.
func (i *Product) RegisterFileDB(file *models.Product) (uint, error) {
	return database.RegisterProduct(file)
}

// Elimina un producto de la base de datos.
func (i *Product) RemoveFileDB(id uint) (*models.Product, error) {
	return database.RemoveProduct(id)
}

// Actualiza un producto en la base de datos.
func (i *Product) UpdateFileDB(id uint, file *models.Product) error {
	return database.UpdateProduct(id, file)
}

// Obtiene un producto por su ID de la base de datos.
func (i *Product) GetFileByIdDB(id uint) (*models.Product, error) {
	return database.GetProductById(id)
}

// Obtiene un producto por su hash de la base de datos.
func (i *Product) GetFileByHashDB(hash string) (*models.Product, error) {
	return database.GetFileByHashDB(hash)
}

// Es una instancia de files.Files para manejar productos.
var ProductArchive files.Files[models.ProductMetadata, *models.Product] = &Product{}

// Añade las rutas relacionadas con los productos al grupo de rutas proporcionado.
func addProductRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/products")

	// GET /products - Obtiene todos los productos
	group.GET("", func(c *gin.Context) {
		products, err := database.GetProducts()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"folder":   files.GetURL(ProductArchive),
			"products": products,
		})
	})

	// GET /products/:id - Obtiene un producto específico
	group.GET("/:id", auth.GetMiddleware(ClientAuth), func(c *gin.Context) {
		productIDStr := c.Param("id")
		productID, err := strconv.Atoi(productIDStr)
		if err != nil {
			c.JSON(400, gin.H{"error": "Invalid product ID"})
			return
		}

		product, err := database.GetProductById(uint(productID))
		if err != nil {
			c.JSON(404, gin.H{"error": "Product not found"})
			return
		}

		c.JSON(200, gin.H{
			"folder":  files.GetURL(ProductArchive),
			"product": product,
		})
	})

	// GET /products/random - Obtiene productos aleatorios para un cliente
	group.GET("/random", auth.GetMiddleware(ClientAuth), middlewares.GetClientID(), func(c *gin.Context) {
		clientID, exists := c.MustGet("clientID").(uint)
		if !exists {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			return
		}

		products, err := database.GetRandomProducts(clientID)
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}

		c.JSON(200, gin.H{
			"folder":   files.GetURL(ProductArchive),
			"products": products,
		})
	})

	// Rutas para subir, actualizar y eliminar archivos de productos (solo para administradores)
	files.UploadFile(ProductArchive, group, auth.GetMiddleware(AdminAuth))
	files.UpdateFile(ProductArchive, group, auth.GetMiddleware(AdminAuth))
	files.DeleteFile(ProductArchive, group, auth.GetMiddleware(AdminAuth))
	files.UpdateMetadata(ProductArchive, group, auth.GetMiddleware(AdminAuth))

	// Servir archivos estáticos
	files.ServeStaticFiles(ProductArchive, router)
}
