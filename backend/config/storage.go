// Este archivo contiene funciones relacionadas con la configuración del
// almacenamiento de archivos en el sistema.
// Autores:
//   - Carlos Zamudio
package config

import (
	"backend/resources/files"
	"os"
)

// Crea la carpeta de almacenamiento si no existe.
func CreateStorageFolder() {
	if _, err := os.Stat(files.StoragePath); os.IsNotExist(err) {
		err := os.Mkdir(files.StoragePath, 0755)
		if err != nil {
			panic(err)
		}
	}
}
