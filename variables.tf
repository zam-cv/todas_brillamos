variable "digitalocean_token" {
  description = "DigitalOcean API Token"
  type        = string
}

variable "region" {
  description = "DigitalOcean region"
  type        = string
}

variable "postgres_user" {
  description = "PostgreSQL database user"
  type        = string
}

variable "postgres_db_name" {
  description = "PostgreSQL database name"
  type        = string
}

variable "admin_email" {
  description = "Admin email for the application"
  type        = string
}

variable "admin_password" {
  description = "Admin password for the application"
  type        = string
}

variable "admin_secret_key" {
  description = "Secret key for admin authentication"
  type        = string
}

variable "admin_token_cookie_name" {
  description = "Cookie name for admin token"
  type        = string
}

variable "client_secret_key" {
  description = "Secret key for client authentication"
  type        = string
}

variable "client_token_cookie_name" {
  description = "Cookie name for client token"
  type        = string
}

variable "stripe_secret_key" {
  description = "Stripe secret key for payments"
  type        = string
}

variable "port" {
  description = "Port for the application"
  type        = number
}