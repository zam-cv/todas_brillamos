#!/bin/bash

# Función para verificar si un servicio está listo
wait_for_service() {
    local host=$1
    local port=$2
    local service=$3
    local max_attempts=30
    local delay=2

    echo "Waiting for $service to be ready..."
    for i in $(seq 1 $max_attempts); do
        if timeout 1 bash -c ">/dev/tcp/$host/$port" 2>/dev/null; then
            echo "$service is ready!"
            return 0
        fi
        echo "Attempt $i/$max_attempts: $service not ready. Waiting..."
        sleep $delay
    done
    echo "$service failed to start after $max_attempts attempts."
    return 1
}

# Iniciar Ollama
ollama serve &

# Esperar a que Ollama esté listo
wait_for_service localhost 11434 "Ollama"

# Descargar el modelo all-minilm
if [ $? -eq 0 ]; then
    echo "Downloading all-minilm model..."
    ollama pull all-minilm
else
    echo "Ollama is not ready. Skipping model download."
    exit 1
fi

# Iniciar ChromaDB
chroma run --host 0.0.0.0 --port 8080 --path /app/datadb &

# Esperar a que ChromaDB esté listo
wait_for_service 0.0.0.0 8080 "ChromaDB"

# Si ambos servicios están listos, iniciar la aplicación backend
if [ $? -eq 0 ]; then
    echo "All services are ready. Starting the backend application..."
    cd /app/backend
    ./main
else
    echo "Failed to start all required services. Exiting."
    exit 1
fi