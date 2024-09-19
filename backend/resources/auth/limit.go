package auth

import (
	"context"
	"errors"
	"strconv"
	"time"

	"github.com/allegro/bigcache/v3"
)

type Limit struct {
	MaxAttempts   int
	BlockDuration time.Duration
	cache         *bigcache.BigCache
}

func NewLimit(maxAttempts int, blockDuration time.Duration) (*Limit, error) {
	ctx := context.Background()
	cache, err := bigcache.New(ctx, bigcache.DefaultConfig(blockDuration))
	if err != nil {
		return nil, err
	}

	return &Limit{
		MaxAttempts:   maxAttempts,
		BlockDuration: blockDuration,
		cache:         cache,
	}, nil
}

func GetAttempts(l *Limit, key string) int {
	attempts, err := l.cache.Get(key)
	if err != nil {
		return 0
	}

	count, _ := strconv.Atoi(string(attempts))
	return count
}

func IncrementAttempts(l *Limit, key string) error {
	count := GetAttempts(l, key) + 1
	if count > l.MaxAttempts {
		return errors.New("max attempts reached")
	} else {
		l.cache.Set(key, []byte(strconv.Itoa(count)))
		return nil
	}
}

func ResetAttempts(l *Limit, key string) {
	l.cache.Delete(key)
}
