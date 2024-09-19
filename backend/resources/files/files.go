package files

import (
	"backend/resources"
	"log"
	"mime/multipart"
	"net/http"
	"os"
	"strconv"

	"github.com/gin-gonic/gin"
)

type FileConfig struct {
	Folder        string
	OptionalFile  bool
	AllowedTypes  map[FileType]struct{}
	FileSizeLimit int64 // in bytes
}

type File[M any] interface {
	SetID(uint)
	GetHash() *string
	GetType() *string
	SetMetadata(*M)
}

type Files[M any, T File[M]] interface {
	GetConfig() *FileConfig
	NewFile() T
	ProcessMetadata(*M) error
	RegisterFileDB(file T) (uint, error)
	RemoveFileDB(id uint) (T, error)
	UpdateFileDB(id uint, file T) error
	GetFileByIdDB(id uint) (T, error)
	GetFileByHashDB(hash string) (T, error)
}

type UploadForm[M any] struct {
	File     *multipart.FileHeader `form:"file"`
	Metadata *M                    `form:"metadata"`
}

func GetURL[M any, T File[M]](files Files[M, T]) string {
	return RootURL + "/" + files.GetConfig().Folder
}

func UploadFile[M any, T File[M]](files Files[M, T], group *gin.RouterGroup, handlers ...gin.HandlerFunc) {
	config := files.GetConfig()
	numberTypes := len(config.AllowedTypes)

	// Create folder if it doesn't exist
	if _, err := os.Stat(StoragePath + "/" + config.Folder); os.IsNotExist(err) {
		os.Mkdir(StoragePath+"/"+config.Folder, 0755)
	}

	group.POST("/upload", append(handlers, func(c *gin.Context) {
		form, err := Validate[M](c)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusBadRequest)
			return
		}

		err = files.ProcessMetadata(form.Metadata)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusBadRequest)
			return
		}

		var fileBytes []byte
		var hash, extension string = "", ""
		duplicateFiles := false

		if form.File != nil {
			fileBytes, hash, extension, err = ProcessFile(form.File, config.FileSizeLimit, config.AllowedTypes)
			if err != nil {
				log.Println(err)
				c.Status(http.StatusBadRequest)
				return
			}

			otherFile, err := files.GetFileByHashDB(hash)
			if err != nil || *otherFile.GetType() != extension {
				err = SaveFileToSystem(config.Folder, fileBytes, hash, extension)
				if err != nil {
					log.Println(err)
					c.Status(http.StatusBadRequest)
					return
				}
			} else {
				duplicateFiles = true
			}
		} else if !config.OptionalFile {
			c.JSON(http.StatusBadRequest, gin.H{"error": "File is required"})
			return
		}

		file := CreateFileObject(files, hash, extension, form.Metadata, config.AllowedTypes, numberTypes)

		id, err := files.RegisterFileDB(file)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusInternalServerError)

			if form.File != nil && !duplicateFiles {
				DeleteSystemFile(config.Folder, hash, extension)
			}

			return
		}

		file.SetID(id)
		c.JSON(
			http.StatusCreated,
			gin.H{"id": id, "name": GetName(hash, extension)},
		)
	})...)
}

func DeleteFile[M any, T File[M]](files Files[M, T], group *gin.RouterGroup, handlers ...gin.HandlerFunc) {
	config := files.GetConfig()

	group.DELETE("/:id", append(handlers, func(c *gin.Context) {
		id := c.Param("id")

		fileID, err := strconv.ParseUint(id, 10, 32)
		if err != nil {
			c.Status(http.StatusBadRequest)
			return
		}

		file, err := files.RemoveFileDB(uint(fileID))
		if err != nil {
			c.Status(http.StatusInternalServerError)
			return
		}

		hash := file.GetHash()
		fileType := file.GetType()

		if hash == nil || fileType == nil {
			c.Status(http.StatusInternalServerError)
			return
		}

		if *hash != "" {
			otherFile, err := files.GetFileByHashDB(*hash)
			if err != nil || *otherFile.GetType() != *fileType {
				err = DeleteSystemFile(config.Folder, *hash, *fileType)
				if err != nil {
					log.Printf("Failed to delete file from system: %v", err)
					c.Status(http.StatusInternalServerError)
					return
				}
			}
		}

		c.JSON(http.StatusOK, gin.H{"message": "File deleted successfully"})
	})...)
}

func UpdateFile[M any, T File[M]](files Files[M, T], group *gin.RouterGroup, handlers ...gin.HandlerFunc) {
	config := files.GetConfig()
	numberTypes := len(config.AllowedTypes)

	group.PUT("/:id", append(handlers, func(c *gin.Context) {
		idStr := c.Param("id")
		fileID, err := strconv.ParseUint(idStr, 10, 32)
		if err != nil {
			c.Status(http.StatusBadRequest)
			return
		}

		id := uint(fileID)

		oldFile, err := files.GetFileByIdDB(id)
		if err != nil {
			c.Status(http.StatusNotFound)
			return
		}

		oldHash := oldFile.GetHash()
		oldFileType := oldFile.GetType()

		if oldHash == nil || oldFileType == nil {
			c.Status(http.StatusInternalServerError)
			return
		}

		form, err := Validate[M](c)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusBadRequest)
			return
		}

		err = files.ProcessMetadata(form.Metadata)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusBadRequest)
			return
		}

		var fileBytes []byte
		var newHash, newExtension string = "", ""

		if form.File != nil {
			fileBytes, newHash, newExtension, err = ProcessFile(form.File, config.FileSizeLimit, config.AllowedTypes)
			if err != nil {
				log.Println(err)
				c.Status(http.StatusBadRequest)
				return
			}

			_, err = files.GetFileByHashDB(newHash)
			fileExists := err == nil

			if !fileExists {
				err = SaveFileToSystem(config.Folder, fileBytes, newHash, newExtension)
				if err != nil {
					log.Println(err)
					c.Status(http.StatusBadRequest)
					return
				}
			}
		} else if !config.OptionalFile {
			c.JSON(http.StatusBadRequest, gin.H{"error": "File is required"})
			return
		}

		newFile := CreateFileObject(files, newHash, newExtension, form.Metadata, config.AllowedTypes, numberTypes)

		err = files.UpdateFileDB(id, newFile)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusInternalServerError)

			if form.File != nil {
				DeleteSystemFile(config.Folder, newHash, newExtension)
			}

			return
		}

		_, err = files.GetFileByHashDB(*oldHash)
		oldFileStillReferenced := err == nil

		if (*oldHash != newHash || *oldFileType != newExtension) && !oldFileStillReferenced {
			err = DeleteSystemFile(config.Folder, *oldHash, *oldFileType)
			if err != nil {
				log.Printf("Failed to delete old file from system: %v", err)
			}
		}

		c.JSON(http.StatusOK, gin.H{"id": id, "name": GetName(newHash, newExtension)})
	})...)
}

func UpdateMetadata[M any, T File[M]](files Files[M, T], group *gin.RouterGroup, handlers ...gin.HandlerFunc) {
	group.POST("/:id/metadata", append(handlers, func(c *gin.Context) {
		idStr := c.Param("id")
		fileID, err := strconv.ParseUint(idStr, 10, 32)
		if err != nil {
			c.Status(http.StatusBadRequest)
			return
		}

		id := uint(fileID)

		var metadata M
		if err := c.ShouldBindJSON(&metadata); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid metadata format"})
			return
		}

		if err := resources.ValidateStruct(&metadata); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid metadata"})
			return
		}

		err = files.ProcessMetadata(&metadata)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusBadRequest)
			return
		}

		file, err := files.GetFileByIdDB(id)
		if err != nil {
			c.Status(http.StatusNotFound)
			return
		}

		file.SetMetadata(&metadata)

		err = files.UpdateFileDB(id, file)
		if err != nil {
			log.Println(err)
			c.Status(http.StatusInternalServerError)
			return
		}

		c.JSON(http.StatusOK, gin.H{"message": "Metadata updated successfully"})
	})...)
}

func ServeStaticFiles[M any, T File[M]](files Files[M, T], router *gin.Engine, authMiddleware ...gin.HandlerFunc) {
	config := files.GetConfig()
	URL := RootURL + "/" + config.Folder
	Path := "./" + StoragePath + "/" + config.Folder

	if len(authMiddleware) == 0 {
		router.Static(URL, Path)
		return
	} else {
		SetupFlexibleFileServer(router, URL, Path, authMiddleware)
	}
}
