package mx.cazv.todasbrillamos.model.services

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserServiceIntegrationTest {
    private lateinit var userService: UserService
    private lateinit var authService: AuthService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        userService = UserService()
        authService = AuthService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testGetClient() = runBlocking {
        val clientDetails = userService.getClient(token)
        assertNotNull("Los detalles del cliente no deberían ser null", clientDetails)
        assertTrue("El nombre del cliente no debería estar vacío", clientDetails.first_name.isNotEmpty())
        assertTrue("El apellido del cliente no debería estar vacío", clientDetails.last_name.isNotEmpty())
        assertTrue("El email del cliente no debería estar vacío", clientDetails.email.isNotEmpty())
    }

    @Test
    fun testUpdateClient() = runBlocking {
        val originalDetails = userService.getClient(token)
        val updatedDetails = originalDetails.copy(first_name = "NuevoNombre", last_name = "NuevoApellido")

        userService.update(token, updatedDetails)

        val newDetails = userService.getClient(token)
        assertEquals("El nombre del cliente debería haber sido actualizado", "NuevoNombre", newDetails.first_name)
        assertEquals("El apellido del cliente debería haber sido actualizado", "NuevoApellido", newDetails.last_name)

        // Revertimos los cambios
        userService.update(token, originalDetails)
    }

    @Test
    fun testUpdatePassword() = runBlocking {
        val passwordUpdate = PasswordUpdate(old_password = "password123", new_password = "newPassword123")
        userService.updatePassword(token, passwordUpdate)

        // Verificamos que podemos iniciar sesión con la nueva contraseña
        val newCredentials = authService.signin(SignInRequest("test@example.com", "newPassword123"))
        assertNotNull("Deberíamos poder iniciar sesión con la nueva contraseña", newCredentials)

        // Revertimos los cambios
        userService.updatePassword(token, PasswordUpdate(old_password = "newPassword123", new_password = "password123"))
    }

    @Test
    fun testExist() = runBlocking {
        val exist = userService.exist(token)
        assertNotNull("El resultado de exist no debería ser null", exist)
    }

    @Test
    fun testSetOthers() = runBlocking {
        var others = Others(
            CURP = "AHDGNABDJHAK347SHDPO",
            Street = "Av. Insurgentes Sur",
            Interior = 404,
            Exterior = 1234,
            City = "Ciudad de México",
            State = "CDMX",
            ZIP = "03100",
            Reference = "Cerca del Parque Hundido"
        )
        var result = userService.setOthers(token, others)

        assertFalse("La operación setOthers fallará porque el CURP es inválido", result)

        others = Others(
            CURP = "AHDGNABDJHAK347SHD",
            Street = "Av. Insurgentes Sur",
            Interior = 404,
            Exterior = 1234,
            City = "Ciudad de México",
            State = "CDMX",
            ZIP = "03100",
            Reference = "Cerca del Parque Hundido"
        )
        result = userService.setOthers(token, others)
        assertTrue("La operación setOthers debería ser exitosa", result)

        // Verificamos que los datos se hayan guardado correctamente
        val address = userService.getAddress(token)
        assertTrue("La dirección debería contener la calle", address.contains("Av. Insurgentes Sur"))
        assertTrue("La dirección debería contener la ciudad", address.contains("Ciudad de México"))
    }

    @Test(expected = Exception::class)
    fun testInvalidToken(): Unit = runBlocking {
        userService.getClient("invalid_token")
    }
}