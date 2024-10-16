# Todas brillamos

## Development

### Prerequisites

- [Docker](https://docs.docker.com/engine/install/)
- [Git](https://git-scm.com/downloads)
- [VSCode](https://code.visualstudio.com/download)
- [Kotlin](https://kotlinlang.org/docs/getting-started.html#choose-your-kotlin-use-case)
- [Python](https://www.python.org/downloads/)
- [Ollama](https://ollama.com/)

### Getting Started

1. Install

For Linux and MacOS, run the following commands in terminal:

```bash
sh <(curl -L https://nixos.org/nix/install) --daemon --yes
. /nix/var/nix/profiles/default/etc/profile.d/nix-daemon.sh
nix-env -iA nixpkgs.go
nix-env -iA nixpkgs.nodejs_22
echo 'export PATH=$HOME/.nix-profile/bin:$PATH' >> ~/.bashrc # or ~/.zshrc
source ~/.bashrc # or ~/.zshrc
```

For Windows, run the following commands in cmd:

```bash
powershell -NoProfile -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))"
# close and reopen cmd
choco install golang nodejs -y
```

2. Clone the repository

```bash
git clone https://github.com/zam-cv/todas_brillamos
```


3. Change to the project directory

```bash
# Open the project directory in VSCode or use the terminal
code todas_brillamos
```

4. Create a `.env` file in the `backend` directory with the following content

```bash
PORT=8000

DB_HOST=localhost
DB_USER=admin
DB_PASSWORD=awdrqwer12
DB_NAME=todasbrillamos
DB_PORT=5432

ADMIN_EMAIL=admin@todasbrillamos.com
ADMIN_PASSWORD=awdrqwer12

ADMIN_SECRET_KEY=secret_admin
ADMIN_TOKEN_COOKIE_NAME=token_admin
CLIENT_SECRET_KEY=secret
CLIENT_TOKEN_COOKIE_NAME=token
STRIPE_SECRET_KEY=sk_test_51Q7usUL7iE9WvPEQaoGeGY1XGBUHBypQ69iLA7nDJ3wvl0i4r5doVmTv5iu24oPRMcbTS0pmB79fgQEPgl6Xgq0D004M272XQ0

API_KEY_MAILER=mlsn.example
EMAIL_MAILER=todasbrillamos@example.com
```

5. Create a `.env` file in the `platform` directory with the following content
```bash
VITE_APP_SERVER_PROTOCOL = "http"
VITE_APP_SERVER_PORT = "8000"
VITE_APP_SERVER_HOST = "localhost"
VITE_APP_API_ROUTE = "api"
```

6. Install the project dependencies

```bash
cd platform
npm install
```

7. Install 

```bash
ollama pull all-minilm:latest
pip install chromadb
```

8. Start the project

```bash
chroma run --host localhost --port 8080 --path ./datadb
```

```bash
docker run --name todasbrillamos-postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=awdrqwer12 -e POSTGRES_DB=todasbrillamos -p 5432:5432 -d postgres
```

```bash
cd platform
npm run dev
```

```bash
cd backend
go run main.go
```

```bash
# run the application in android studio
```

### Testing

1. Install the dependencies

```bash
# For Linux
INSTALL_DIR=/tmp
VERSION=5.0.1
curl --silent --location https://github.com/Orange-OpenSource/hurl/releases/download/$VERSION/hurl-$VERSION-x86_64-unknown-linux-gnu.tar.gz | tar xvz -C $INSTALL_DIR
export PATH=$INSTALL_DIR/hurl-$VERSION-x86_64-unknown-linux-gnu/bin:$PATH

# For MacOS
brew install hurl

# For Windows
choco install hurl
```

2. Run the tests

Create a `.env` file in the `backend/tests/api` directory with the following content

```bash
PORT=8000

DB_HOST=localhost
DB_USER=admin
DB_PASSWORD=awdrqwer12
DB_NAME=todasbrillamos
DB_PORT=5432

ADMIN_EMAIL=admin@todasbrillamos.com
ADMIN_PASSWORD=awdrqwer12

ADMIN_SECRET_KEY=secret_admin
ADMIN_TOKEN_COOKIE_NAME=token_admin
CLIENT_SECRET_KEY=secret
CLIENT_TOKEN_COOKIE_NAME=token
STRIPE_SECRET_KEY=sk_test_51Q7usUL7iE9WvPEQaoGeGY1XGBUHBypQ69iLA7nDJ3wvl0i4r5doVmTv5iu24oPRMcbTS0pmB79fgQEPgl6Xgq0D004M272XQ0

API_KEY_MAILER=mlsn.example
EMAIL_MAILER=todasbrillamos@example.com
```

3. Run the tests

```bash
cd backend
go test ./tests
go test ./tests/api/main_test.go
hurl --test --color ./tests/api/*.hurl
```