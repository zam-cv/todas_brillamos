// Funciones para el manejo de archivos.
// Autores:
//   - Carlos Zamudio

package files

import (
	"backend/resources"
	"crypto/sha256"
	"encoding/hex"
	"errors"
	"io"
	"mime/multipart"
	"net/http"
	"os"
	"path/filepath"

	"github.com/gin-gonic/gin"
	"github.com/h2non/filetype"
)

// Representa el tipo de archivo.
type FileType string

// Constantes para los diferentes tipos de archivos soportados.
const (
	JPG    FileType = "jpg"
	JPEG   FileType = "jpeg"
	PNG    FileType = "png"
	GIF    FileType = "gif"
	PDF    FileType = "pdf"
	CSV    FileType = "csv"
	DOCX   FileType = "docx"
	XLSX   FileType = "xlsx"
	PPTX   FileType = "pptx"
	MP4    FileType = "mp4"
	MP3    FileType = "mp3"
	BINARY FileType = ""
	ALL    FileType = "*"
)

// Obtiene el nombre del archivo basado en su hash y extensión.
func GetName(hash, extension string) string {
	if extension == "" {
		return hash
	}
	return hash + "." + extension
}

// Procesa un archivo subido, validando su tamaño y tipo.
// Devuelve los bytes del archivo, su hash, su extensión y un error en caso de que ocurra.
func ProcessFile(
	file *multipart.FileHeader,
	sizeLimit int64,
	allowedTypes map[FileType]struct{},
) ([]byte, string, string, error) {
	body, err := file.Open()
	if err != nil {
		return nil, "", "", errors.New("could not open file")
	}

	limitedReader := io.LimitReader(body, sizeLimit+1)
	fileBytes, err := io.ReadAll(limitedReader)
	if err != nil {
		return nil, "", "", errors.New("error reading file")
	}

	if len(fileBytes) > int(sizeLimit) {
		return nil, "", "", errors.New("file size exceeds the limit")
	}

	kind, _ := filetype.Match(fileBytes)
	if kind == filetype.Unknown {
		return nil, "", "", errors.New("unknown file type")
	}

	if _, ok := allowedTypes[ALL]; !ok {
		if _, ok := allowedTypes[FileType(kind.Extension)]; !ok {
			return nil, "", "", errors.New("file type not allowed")
		}
	}

	// TODO: Remove metadata from the file

	hash := sha256.New()
	hash.Write(fileBytes)
	hashBytes := hash.Sum(nil)
	hashString := hex.EncodeToString(hashBytes)

	return fileBytes, hashString, kind.Extension, nil
}

// Guarda un archivo en el sistema de archivos.
func SaveFileToSystem(
	folder string,
	fileBytes []byte,
	hashString string,
	extension string,
) error {
	name := GetName(hashString, extension)
	err := os.WriteFile(StoragePath+"/"+folder+"/"+name, fileBytes, 0644)
	if err != nil {
		return errors.New("could not save processed file")
	}

	return nil
}

// Elimina un archivo del sistema de archivos.
func DeleteSystemFile(folder, hash, extension string) error {
	filePath := filepath.Join(StoragePath, folder, hash)
	if extension != "" {
		filePath += "." + extension
	}

	if err := os.Remove(filePath); err != nil {
		return errors.New("could not delete file")
	}

	return nil
}

// Valida un formulario de carga de archivos.
func Validate[M any](c *gin.Context) (*UploadForm[M], error) {
	var form UploadForm[M]
	if err := c.ShouldBind(&form); err != nil {
		return nil, err
	}

	if form.Metadata != nil {
		if err := resources.ValidateStruct(form.Metadata); err != nil {
			return nil, err
		}
	}

	return &form, nil
}

// Crea un objeto de archivo.
func CreateFileObject[M any, T File[M]](files Files[M, T], hash string, extension string, metadata *M, allowedTypes map[FileType]struct{}, numberTypes int) T {
	file := files.NewFile()
	if _, ok := allowedTypes[ALL]; ok || numberTypes > 1 {
		*file.GetType() = extension
	}
	*file.GetHash() = hash
	file.SetMetadata(metadata)
	return file
}

// Configura un servidor de archivos flexible.
func SetupFlexibleFileServer(router *gin.Engine, urlPath string, filePath string, authMiddleware []gin.HandlerFunc) {
	if len(authMiddleware) == 0 {
		router.Static(urlPath, filePath)
		return
	}

	if urlPath[len(urlPath)-1] != '/' {
		urlPath += "/"
	}
	urlPath += "*filepath"

	router.GET(urlPath, append(authMiddleware, func(c *gin.Context) {
		relativePath := c.Param("filepath")
		if relativePath == "" {
			c.Status(http.StatusNotFound)
			return
		}

		fullPath := filepath.Join(filePath, relativePath)

		_, err := os.Stat(fullPath)
		if os.IsNotExist(err) {
			c.Status(http.StatusNotFound)
			return
		}

		c.File(fullPath)
	})...)
}
