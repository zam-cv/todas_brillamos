// Limita el número de intentos de autenticación.
// Autores:
//   - Carlos Zamudio

package auth

import (
	"context"
	"errors"
	"strconv"
	"time"

	"github.com/allegro/bigcache/v3"
)

// Representa una estructura para limitar intentos de autenticación.
type Limit struct {
	MaxAttempts   int
	BlockDuration time.Duration
	Cache         *bigcache.BigCache
}

// Crea una nueva instancia de Limit.
// Devuelve un puntero a Limit y un error en caso de que ocurra.
func NewLimit(maxAttempts int, blockDuration time.Duration) (*Limit, error) {
	ctx := context.Background()
	cache, err := bigcache.New(ctx, bigcache.DefaultConfig(blockDuration))
	if err != nil {
		return nil, err
	}

	return &Limit{
		MaxAttempts:   maxAttempts,
		BlockDuration: blockDuration,
		Cache:         cache,
	}, nil
}

// Obtiene el número de intentos de autenticación para una clave dada.
// Devuelve el número de intentos.
func GetAttempts(l *Limit, key string) int {
	attempts, err := l.Cache.Get(key)
	if err != nil {
		return 0
	}

	count, _ := strconv.Atoi(string(attempts))
	return count
}

// Incrementa el número de intentos de autenticación para una clave dada.
// Devuelve un error si se alcanzó el número máximo de intentos.
func IncrementAttempts(l *Limit, key string) error {
	count := GetAttempts(l, key) + 1
	if count > l.MaxAttempts {
		return errors.New("max attempts reached")
	} else {
		l.Cache.Set(key, []byte(strconv.Itoa(count)))
		return nil
	}
}

// Reinicia el número de intentos de autenticación para una clave dada.
func ResetAttempts(l *Limit, key string) {
	l.Cache.Delete(key)
}
