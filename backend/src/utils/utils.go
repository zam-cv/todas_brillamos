package utils

import (
	"fmt"
	"net"
	"net/http"
	"time"

	"github.com/rs/cors"
	"github.com/shirou/gopsutil/host"
)

func SetCookieWithExpiredToken(w http.ResponseWriter) {
	http.SetCookie(w, &http.Cookie{
		Name:     "token",
		Value:    "",
		HttpOnly: true,
		Secure:   true,
		Path:     "/",
		Expires:  time.Now().Add(-24 * time.Hour),
	})
}

func GetOS() (string, error) {
	info, err := host.Info()
	if err != nil {
		return "", fmt.Errorf("failed to get OS information: %v", err)
	}
	return info.OS, nil
}

func GetMyIp() (string, error) {
	addrs, err := net.InterfaceAddrs()
	if err != nil {
		return "", fmt.Errorf("failed to get IP: %v", err)
	}
	for _, addr := range addrs {
		if ipnet, ok := addr.(*net.IPNet); ok && !ipnet.IP.IsLoopback() {
			if ipnet.IP.To4() != nil {
				return ipnet.IP.String(), nil
			}
		}
	}
	return "", fmt.Errorf("no IP found")
}

func GetCORS() *cors.Cors {
	return cors.New(cors.Options{
		AllowedMethods:   []string{"GET", "POST", "PUT", "DELETE"},
		AllowedHeaders:   []string{"Authorization", "Content-Type"},
		AllowCredentials: true,
	})
}
