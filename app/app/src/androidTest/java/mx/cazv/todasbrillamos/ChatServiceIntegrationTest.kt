package mx.cazv.todasbrillamos.model.services

import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChatServiceIntegrationTest {
    private lateinit var chatService: ChatService
    private lateinit var authService: AuthService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        chatService = ChatService()
        authService = AuthService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testSendMessage() = runBlocking {
        val content = "Hola"
        val response = chatService.sendMessage(token, content)

        assertNotNull("La respuesta no debería ser null", response)
        assertTrue("La respuesta debería contener algún texto", response.isNotEmpty())
    }

    @Test(expected = Exception::class)
    fun testSendMessageWithInvalidToken(): Unit = runBlocking {
        val content = "Este mensaje no debería enviarse"
        chatService.sendMessage("invalid_token", content)
    }

    @Test
    fun testSendEmptyMessage() = runBlocking {
        val content = ""
        val response = chatService.sendMessage(token, content)

        assertNotNull("La respuesta no debería ser null incluso para un mensaje vacío", response)
    }

    @Test
    fun testSendLongMessage() = runBlocking {
        val content = "a".repeat(1000) // Un mensaje muy largo
        val response = chatService.sendMessage(token, content)

        assertNotNull("La respuesta no debería ser null para un mensaje largo", response)
    }
}