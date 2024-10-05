package routes

import (
	"backend/database"
	"backend/middlewares"
	"backend/models"
	"backend/resources/auth"
	"backend/resources/files"
	"net/http"

	"github.com/gin-gonic/gin"
)

type Product struct{}

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

func (i *Product) NewFile() *models.Product {
	return &models.Product{}
}

func (i *Product) ProcessMetadata(metadata *models.ProductMetadata) error {
	if _, err := database.GetCategoryByID(int(metadata.CategoryID)); err != nil {
		return err
	}

	return nil
}

func (i *Product) RegisterFileDB(file *models.Product) (uint, error) {
	return database.RegisterProduct(file)
}

func (i *Product) RemoveFileDB(id uint) (*models.Product, error) {
	return database.RemoveProduct(id)
}

func (i *Product) UpdateFileDB(id uint, file *models.Product) error {
	return database.UpdateProduct(id, file)
}

func (i *Product) GetFileByIdDB(id uint) (*models.Product, error) {
	return database.GetProductById(id)
}

func (i *Product) GetFileByHashDB(hash string) (*models.Product, error) {
	return database.GetFileByHashDB(hash)
}

var ProductArchive files.Files[models.ProductMetadata, *models.Product] = &Product{}

func addProductRoutes(rg *gin.RouterGroup) {
	group := rg.Group("/products")

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

	files.UploadFile(ProductArchive, group, auth.GetMiddleware(AdminAuth))
	files.UpdateFile(ProductArchive, group, auth.GetMiddleware(AdminAuth))
	files.DeleteFile(ProductArchive, group, auth.GetMiddleware(AdminAuth))
	files.UpdateMetadata(ProductArchive, group, auth.GetMiddleware(AdminAuth))

	// group.GET("/recommended", func(c *gin.Context) {
	// 	c.JSON(200, gin.H{
	// 		"message": "Get recommended products",
	// 	})
	// })

	// Serve static files
	files.ServeStaticFiles(ProductArchive, router)
}
