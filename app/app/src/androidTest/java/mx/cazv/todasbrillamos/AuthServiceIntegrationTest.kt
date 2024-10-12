package mx.cazv.todasbrillamos.model.services

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthServiceIntegrationTest {
    private lateinit var authService: AuthService

    @Before
    fun setUp() {
        authService = AuthService()
    }

    @Test
    fun testSignin() = runBlocking {
        val request = SignInRequest("test@example.com", "password123")
        val result = authService.signin(request)
        assertNotNull("El resultado de signin no debería ser null", result)
        assertTrue("Las credenciales deberían contener un token", result?.token?.isNotEmpty() == true)
    }

    @Test
    fun testRegister() = runBlocking {
        val request = SignInRequest("test2@example.com", "password123")
        val result = authService.signin(request)

        if (result != null) {
            val token = result.token
            val verify = authService.verify(token)
            assertTrue("La verificación debería ser exitosa", verify)
        } else {
            val userInfo = UserInfo("test2@example.com", "password123", "John", "Doe")
            val r = authService.register(userInfo)
            assertTrue("El registro debería ser exitoso", r)
        }
    }

    @Test
    fun testVerify() = runBlocking {
        // Primero, obtenemos un token válido a través de signin
        val signinRequest = SignInRequest("test@example.com", "password123")
        val credentials = authService.signin(signinRequest)
        assertNotNull("Las credenciales no deberían ser null", credentials)

        val token = credentials?.token ?: ""
        val result = authService.verify(token)
        assertTrue("La verificación debería ser exitosa", result)
    }

    @Test
    fun testSigninWithInvalidCredentials() = runBlocking {
        val request = SignInRequest("invalid@example.com", "wrongpassword")
        val result = authService.signin(request)
        assertNull("El resultado de signin con credenciales inválidas debería ser null", result)
    }

    @Test
    fun testVerifyWithInvalidToken() = runBlocking {
        val invalidToken = "invalid_token"
        val result = authService.verify(invalidToken)
        assertFalse("La verificación con token inválido debería fallar", result)
    }
}