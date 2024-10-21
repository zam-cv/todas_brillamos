package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.GroupedNotification

/**
 * Data class que representa el estado de las notificaciones.
 *
 * @property groupedNotifications El grupo de notificaciones.
 * @property error El mensaje de error, si existe.
 */
data class NotificationState(
    val groupedNotifications: GroupedNotification = GroupedNotification("", emptyList()),
    val error: String? = null
)