import kotlinx.coroutines.runBlocking
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.services.AuthService
import mx.cazv.todasbrillamos.model.services.NotificationService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotificationServiceIntegrationTest {
    private lateinit var notificationService: NotificationService
    private lateinit var authService: AuthService
    private lateinit var token: String

    @Before
    fun setUp() = runBlocking {
        notificationService = NotificationService()
        authService = AuthService()

        // Obtenemos un token válido para las pruebas
        val credentials = authService.signin(SignInRequest("test@example.com", "password123"))
        token = credentials?.token ?: throw IllegalStateException("No se pudo obtener un token válido para las pruebas")
    }

    @Test
    fun testGetNotifications() = runBlocking {
        val notifications = notificationService.getNotifications(token)

        assertNotNull("La lista de notificaciones no debería ser null", notifications)
        assertTrue("La lista de notificaciones no debería estar vacía", notifications.isNotEmpty())
    }

    @Test(expected = Exception::class)
    fun testGetNotificationsWithInvalidToken(): Unit = runBlocking {
        notificationService.getNotifications("invalid_token")
    }

    @Test
    fun testNotificationGrouping() = runBlocking {
        val notifications = notificationService.getNotifications(token)

        assertNotNull("La lista de notificaciones agrupadas no debería ser null", notifications)
        assertTrue("Debería haber al menos un grupo de notificaciones", notifications.isNotEmpty())

        // Verifica que cada grupo tiene al menos una notificación
        notifications.forEach { group ->
            assertTrue("Cada grupo debería tener al menos una notificación", group.notifications.isNotEmpty())
        }
    }

    @Test
    fun testNotificationAttributes() = runBlocking {
        val notifications = notificationService.getNotifications(token)

        assertFalse("Debería haber notificaciones para verificar", notifications.isEmpty())

        val firstNotification = notifications.first().notifications.first()
        assertNotNull("El contenido de la notificación no debería ser null", firstNotification.description)
        assertNotNull("La hora de la notificación no debería ser null", firstNotification.hour)
    }
}