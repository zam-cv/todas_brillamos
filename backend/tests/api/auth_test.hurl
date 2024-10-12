# Pruebas de autenticación

# Login de administrador
POST http://localhost:8000/api/auth/admin/signin
{
    "email": "admin@todasbrillamos.com",
    "password": "awdrqwer12"
}

HTTP 200
[Captures]
admin_token: jsonpath "$.token"

# Verificar administrador
GET http://localhost:8000/api/auth/admin/verify
Authorization: Bearer {{admin_token}}

HTTP 200

# Login de usuario
POST http://localhost:8000/api/auth/user/signin
{
    "email": "testuser@example.com",
    "password": "testpassword123"
}

HTTP 200
[Captures]
user_token: jsonpath "$.token"

# Verificar usuario
GET http://localhost:8000/api/auth/user/verify
Authorization: Bearer {{user_token}}

HTTP 200