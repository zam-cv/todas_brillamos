package integration

import (
	"backend/resources"
	"backend/resources/auth"
	"backend/resources/files"
	"bytes"
	"os"
	"path/filepath"
	"testing"
	"time"
)

func TestHashPassword(t *testing.T) {
	password := "mySecurePassword123"
	hash, err := auth.HashPassword(password)
	if err != nil {
		t.Errorf("Error hashing password: %v", err)
	}
	if hash == password {
		t.Error("Hashed password should not be equal to original password")
	}
}

func TestCheckPasswordHash(t *testing.T) {
	password := "mySecurePassword123"
	hash, _ := auth.HashPassword(password)

	if !auth.CheckPasswordHash(password, hash) {
		t.Error("Password and hash should match")
	}

	if auth.CheckPasswordHash("wrongPassword", hash) {
		t.Error("Wrong password should not match hash")
	}
}

func TestGenerateToken(t *testing.T) {
	id := "user123"
	secret := "mySecretKey"

	token, err := auth.GenerateToken(id, secret)
	if err != nil {
		t.Errorf("Error generating token: %v", err)
	}
	if token == "" {
		t.Error("Generated token should not be empty")
	}
}

func TestParseToken(t *testing.T) {
	id := "user123"
	secret := "mySecretKey"

	token, _ := auth.GenerateToken(id, secret)
	parsedID, err := auth.ParseToken(token, secret)

	if err != nil {
		t.Errorf("Error parsing token: %v", err)
	}
	if parsedID != id {
		t.Errorf("Parsed ID %s does not match original ID %s", parsedID, id)
	}

	_, err = auth.ParseToken("invalidToken", secret)
	if err == nil {
		t.Error("Parsing invalid token should return an error")
	}
}

type TestStruct struct {
	Name  string `validate:"required"`
	Email string `validate:"required,email"`
	Age   int    `validate:"gte=0,lte=130"`
}

func TestValidateStruct(t *testing.T) {
	// Inicializar el validador
	resources.InitValidator()

	// Caso de prueba 1: Estructura válida
	validStruct := TestStruct{
		Name:  "John Doe",
		Email: "john@example.com",
		Age:   30,
	}
	err := resources.ValidateStruct(validStruct)
	if err != nil {
		t.Errorf("Se esperaba que la estructura fuera válida, pero se obtuvo un error: %v", err)
	}

	// Caso de prueba 2: Nombre faltante (requerido)
	invalidStruct1 := TestStruct{
		Email: "jane@example.com",
		Age:   25,
	}
	err = resources.ValidateStruct(invalidStruct1)
	if err == nil {
		t.Error("Se esperaba un error debido al nombre faltante, pero no se obtuvo ninguno")
	}

	// Caso de prueba 3: Email inválido
	invalidStruct2 := TestStruct{
		Name:  "Bob Smith",
		Email: "not-an-email",
		Age:   40,
	}
	err = resources.ValidateStruct(invalidStruct2)
	if err == nil {
		t.Error("Se esperaba un error debido al email inválido, pero no se obtuvo ninguno")
	}

	// Caso de prueba 4: Edad fuera de rango
	invalidStruct3 := TestStruct{
		Name:  "Alice Johnson",
		Email: "alice@example.com",
		Age:   150,
	}
	err = resources.ValidateStruct(invalidStruct3)
	if err == nil {
		t.Error("Se esperaba un error debido a la edad fuera de rango, pero no se obtuvo ninguno")
	}
}

func TestNewLimit(t *testing.T) {
	maxAttempts := 3
	blockDuration := time.Minute

	limit, err := auth.NewLimit(maxAttempts, blockDuration)
	if err != nil {
		t.Fatalf("NewLimit returned an error: %v", err)
	}

	if limit.MaxAttempts != maxAttempts {
		t.Errorf("Expected MaxAttempts to be %d, got %d", maxAttempts, limit.MaxAttempts)
	}

	if limit.BlockDuration != blockDuration {
		t.Errorf("Expected BlockDuration to be %v, got %v", blockDuration, limit.BlockDuration)
	}

	if limit.Cache == nil {
		t.Error("Expected cache to be initialized, got nil")
	}
}

func TestGetAttempts(t *testing.T) {
	limit, _ := auth.NewLimit(3, time.Minute)
	key := "test_key"

	// Prueba cuando no hay intentos previos
	attempts := auth.GetAttempts(limit, key)
	if attempts != 0 {
		t.Errorf("Expected 0 attempts for a new key, got %d", attempts)
	}

	// Prueba después de incrementar los intentos
	limit.Cache.Set(key, []byte("2"))
	attempts = auth.GetAttempts(limit, key)
	if attempts != 2 {
		t.Errorf("Expected 2 attempts, got %d", attempts)
	}
}

func TestIncrementAttempts(t *testing.T) {
	limit, _ := auth.NewLimit(3, time.Minute)
	key := "test_key"

	// Prueba incremento normal
	for i := 1; i <= 3; i++ {
		err := auth.IncrementAttempts(limit, key)
		if err != nil {
			t.Errorf("Unexpected error on attempt %d: %v", i, err)
		}
		attempts := auth.GetAttempts(limit, key)
		if attempts != i {
			t.Errorf("Expected %d attempts, got %d", i, attempts)
		}
	}

	// Prueba exceder el límite
	err := auth.IncrementAttempts(limit, key)
	if err == nil {
		t.Error("Expected error when exceeding max attempts, got nil")
	}
}

func TestResetAttempts(t *testing.T) {
	limit, _ := auth.NewLimit(3, time.Minute)
	key := "test_key"

	// Incrementar intentos
	auth.IncrementAttempts(limit, key)
	auth.IncrementAttempts(limit, key)

	// Verificar que hay intentos
	attempts := auth.GetAttempts(limit, key)
	if attempts != 2 {
		t.Errorf("Expected 2 attempts before reset, got %d", attempts)
	}

	// Resetear intentos
	auth.ResetAttempts(limit, key)

	// Verificar que los intentos se han reseteado
	attempts = auth.GetAttempts(limit, key)
	if attempts != 0 {
		t.Errorf("Expected 0 attempts after reset, got %d", attempts)
	}
}

func TestSaveFileToSystem(t *testing.T) {
	// Crear un directorio temporal para las pruebas
	tempDir, err := os.MkdirTemp("", "test")
	if err != nil {
		t.Fatal(err)
	}
	defer os.RemoveAll(tempDir)

	// Guardar la ruta de almacenamiento original y restaurarla después de la prueba
	originalStoragePath := files.StoragePath
	files.StoragePath = tempDir
	defer func() { files.StoragePath = originalStoragePath }()

	// Definir los parámetros de prueba
	folder := "testfolder"
	fileBytes := []byte("test content")
	hashString := "testhash"
	extension := "txt"

	// Asegurarse de que el directorio de destino exista
	if err := os.MkdirAll(filepath.Join(tempDir, folder), 0755); err != nil {
		t.Fatal(err)
	}

	// Ejecutar la función
	err = files.SaveFileToSystem(folder, fileBytes, hashString, extension)

	// Verificar los resultados
	if err != nil {
		t.Errorf("SaveFileToSystem returned an error: %v", err)
	}

	// Verificar que el archivo se haya creado correctamente
	expectedPath := filepath.Join(tempDir, folder, files.GetName(hashString, extension))
	if _, err := os.Stat(expectedPath); os.IsNotExist(err) {
		t.Errorf("File was not created at %s", expectedPath)
	}

	// Verificar el contenido del archivo
	savedContent, err := os.ReadFile(expectedPath)
	if err != nil {
		t.Errorf("Error reading saved file: %v", err)
	}
	if !bytes.Equal(savedContent, fileBytes) {
		t.Errorf("Saved content does not match. Expected %v, got %v", fileBytes, savedContent)
	}
}

func TestDeleteSystemFile(t *testing.T) {
	// Crear un directorio temporal para las pruebas
	tempDir, err := os.MkdirTemp("", "test")
	if err != nil {
		t.Fatal(err)
	}
	defer os.RemoveAll(tempDir)

	// Guardar la ruta de almacenamiento original y restaurarla después de la prueba
	originalStoragePath := files.StoragePath
	files.StoragePath = tempDir
	defer func() { files.StoragePath = originalStoragePath }()

	// Definir los parámetros de prueba
	folder := "testfolder"
	hash := "testhash"
	extension := "txt"

	// Crear un archivo de prueba
	testFilePath := filepath.Join(tempDir, folder, hash+"."+extension)
	if err := os.MkdirAll(filepath.Dir(testFilePath), 0755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(testFilePath, []byte("test content"), 0644); err != nil {
		t.Fatal(err)
	}

	// Ejecutar la función
	err = files.DeleteSystemFile(folder, hash, extension)

	// Verificar los resultados
	if err != nil {
		t.Errorf("DeleteSystemFile returned an error: %v", err)
	}

	// Verificar que el archivo se haya eliminado
	if _, err := os.Stat(testFilePath); !os.IsNotExist(err) {
		t.Errorf("File was not deleted at %s", testFilePath)
	}
}
