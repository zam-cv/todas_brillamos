# Todas brillamos

## Development

### Prerequisites

- [Docker](https://docs.docker.com/engine/install/)
- [Git](https://git-scm.com/downloads)
- [VSCode](https://code.visualstudio.com/download)
- [Kotlin](https://kotlinlang.org/docs/getting-started.html#choose-your-kotlin-use-case)

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

ADMIN_EMAIL=admin@todasbrillamos.com
ADMIN_PASSWORD=awdrqwer12

ADMIN_SECRET_KEY=secret_admin
ADMIN_TOKEN_COOKIE_NAME=token_admin
CLIENT_SECRET_KEY=secret
CLIENT_TOKEN_COOKIE_NAME=token
```

5. Install the project dependencies

```bash
cd platform
npm install
```

6. Start the project

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