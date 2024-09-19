package config

import (
	"backend/resources/files"
	"os"
)

func CreateStorageFolder() {
	if _, err := os.Stat(files.StoragePath); os.IsNotExist(err) {
		err := os.Mkdir(files.StoragePath, 0755)
		if err != nil {
			panic(err)
		}
	}
}
