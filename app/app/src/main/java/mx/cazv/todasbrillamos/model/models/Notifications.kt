package mx.cazv.todasbrillamos.model.models

/**
 * Archivo que contiene las clases de datos relacionadas con las notificaciones.
 * @author Mariana Balderrábano, Carlos Zamudio
 */

/**
 * Data class que representa una notificación.
 *
 * @property hour La hora en que se emitió la notificación.
 * @property title El título de la notificación.
 * @property description El contenido de la notificación.
 */
data class NotificationGet(
    val hour: String,
    val title: String,
    val description: String
)

/**
 * Data class que representa un grupo de notificaciones.
 *
 * @property date La fecha en que se emitieron las notificaciones.
 * @property notifications La lista de notificaciones.
 */
data class GroupedNotification(
    val date: String,
    val notifications: List<NotificationGet>
)


/**
 * Data class que representa el número de notificaciones no leídas.
 *
 * @property unread El número de notificaciones no leídas.
 */
data class Unread(
    val unread: Int
)