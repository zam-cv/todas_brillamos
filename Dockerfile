# Usar una imagen base de Golang
FROM golang:1.22.4

# Instalar dependencias del sistema, Python y curl
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    python3-venv \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Crear un entorno virtual de Python
ENV VIRTUAL_ENV=/opt/venv
RUN python3 -m venv $VIRTUAL_ENV
ENV PATH="$VIRTUAL_ENV/bin:$PATH"

# Actualizar pip en el entorno virtual
RUN pip install --upgrade pip

# Instalar ChromaDB en el entorno virtual
RUN pip install chromadb

# Instalar Ollama
RUN curl -fsSL https://ollama.com/install.sh | sh

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Instalar dependencias de Go
RUN cd backend && go mod download

# Compilar la aplicación Go
RUN cd backend && go build -o main .

# Asegurarse de que el script de entrada tiene permisos de ejecución
RUN chmod +x entrypoint.sh

# Exponer los puertos necesarios
EXPOSE 8000 8080 11434

# Usar el script de entrada como punto de entrada
ENTRYPOINT ["./entrypoint.sh"]