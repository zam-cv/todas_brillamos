# Pruebas de otros endpoints

POST http://localhost:8000/api/auth/user/signin
{
    "email": "testuser@example.com",
    "password": "testpassword123"
}

HTTP 200
[Captures]
user_token: jsonpath "$.token"

# Verificar existencia de información adicional
GET http://localhost:8000/api/others/exist
Authorization: Bearer {{user_token}}

HTTP 200

# Establecer información adicional
POST http://localhost:8000/api/others
Authorization: Bearer {{user_token}}
{
    "CURP": "FHEM060926MMCSHNA2",
    "Street": "Calle de prueba",
    "Interior": 1,
    "Exterior": 2,
    "City": "Ciudad de prueba",
    "State": "Estado de prueba",
    "ZIP": "12345",
    "Reference": "Referencia de prueba"
}

HTTP 201

# Obtener información adicional
GET http://localhost:8000/api/others
Authorization: Bearer {{user_token}}

HTTP 200

# Actualizar información adicional
PUT http://localhost:8000/api/others
Authorization: Bearer {{user_token}}
{
    "CURP": "FHEM060926MMCSHNA2",
    "Street": "Calle actualizada",
    "Interior": 3,
    "Exterior": 4,
    "City": "Ciudad actualizada",
    "State": "Estado actualizado",
    "ZIP": "54321",
    "Reference": "Referencia actualizada"
}

HTTP 200

# Obtener dirección
GET http://localhost:8000/api/others/address
Authorization: Bearer {{user_token}}

HTTP 200