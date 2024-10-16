terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "digitalocean" {
  token = var.digitalocean_token
}

resource "digitalocean_vpc" "tb_vpc" {
  name     = "tb-vpc"
  region   = var.region
  ip_range = "10.1.0.0/16"
}

resource "digitalocean_database_cluster" "tb_db" {
  name                 = "tb-db-cluster"
  engine               = "pg"
  version              = "13"
  size                 = "db-s-1vcpu-2gb"
  region               = var.region
  node_count           = 1
  private_network_uuid = digitalocean_vpc.tb_vpc.id

  maintenance_window {
    day  = "sunday"
    hour = "02:00:00"
  }
}

resource "digitalocean_database_user" "tb_db_user" {
  cluster_id = digitalocean_database_cluster.tb_db.id
  name       = var.postgres_user
}

resource "digitalocean_database_db" "tb_db" {
  cluster_id = digitalocean_database_cluster.tb_db.id
  name       = var.postgres_db_name
}

resource "digitalocean_database_firewall" "tb_db_firewall" {
  cluster_id = digitalocean_database_cluster.tb_db.id

  rule {
    type  = "ip_addr"
    value = digitalocean_droplet.app.ipv4_address_private
  }
}

resource "digitalocean_droplet" "app" {
  image    = "ubuntu-22-04-x64"
  name     = "tb-server"
  region   = var.region
  size     = "s-2vcpu-4gb"
  vpc_uuid = digitalocean_vpc.tb_vpc.id

  depends_on = [
    digitalocean_database_cluster.tb_db,
    digitalocean_database_user.tb_db_user,
    digitalocean_database_db.tb_db
  ]

  user_data = <<-EOF
    #!/bin/bash
    apt-get update
    apt-get install -y apt-transport-https ca-certificates curl software-properties-common
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    apt-get update
    apt-get install -y docker-ce
    usermod -aG docker admin
    docker run -d --name app-server \
      --restart always \
      -p ${var.port}:${var.port} \
      -e PORT=${var.port} \
      -e DB_HOST=${digitalocean_database_cluster.tb_db.private_host} \
      -e DB_PORT=${digitalocean_database_cluster.tb_db.port} \
      -e DB_USER=${digitalocean_database_user.tb_db_user.name} \
      -e DB_PASSWORD=${digitalocean_database_user.tb_db_user.password} \
      -e DB_NAME=${var.postgres_db_name} \
      -e ADMIN_EMAIL=${var.admin_email} \
      -e ADMIN_PASSWORD=${var.admin_password} \
      -e ADMIN_SECRET_KEY=${var.admin_secret_key} \
      -e ADMIN_TOKEN_COOKIE_NAME=${var.admin_token_cookie_name} \
      -e CLIENT_SECRET_KEY=${var.client_secret_key} \
      -e CLIENT_TOKEN_COOKIE_NAME=${var.client_token_cookie_name} \
      -e STRIPE_SECRET_KEY=${var.stripe_secret_key} \
      -e API_KEY_MAILER=${var.api_key_mailer} \
      -e EMAIL_MAILER=${var.email_mailer} \
      zamcv/todasbrillamos:latest
  EOF
}

resource "digitalocean_firewall" "public_firewall" {
  name = "public-firewall"
  droplet_ids = [digitalocean_droplet.app.id]

  inbound_rule {
    protocol         = "tcp"
    port_range       = "22"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  inbound_rule {
    protocol         = "tcp"
    port_range       = "80"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  inbound_rule {
    protocol         = "tcp"
    port_range       = "443"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  inbound_rule {
    protocol         = "tcp"
    port_range       = var.port
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "tcp"
    port_range            = "1-65535"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "udp"
    port_range            = "1-65535"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "icmp"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }
}

output "tb_app_public_ip" {
  value = digitalocean_droplet.app.ipv4_address
}

output "database_connection_string" {
  value = "postgres://${digitalocean_database_user.tb_db_user.name}:${digitalocean_database_user.tb_db_user.password}@${digitalocean_database_cluster.tb_db.private_host}:${digitalocean_database_cluster.tb_db.port}/${var.postgres_db_name}"
  sensitive = true
}