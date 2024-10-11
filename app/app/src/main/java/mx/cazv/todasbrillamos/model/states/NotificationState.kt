package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.model.models.GroupedNotification

data class NotificationState(
    val groupedNotifications: GroupedNotification = GroupedNotification("", emptyList()),
    val error: String? = null
)