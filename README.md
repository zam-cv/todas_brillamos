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

4. Install the project dependencies

```bash
cd platform
npm install
```

7. Start the project

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