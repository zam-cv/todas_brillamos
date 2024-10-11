package mx.cazv.todasbrillamos.model.models

data class NotificationGet(
    val hour: String,
    val title: String,
    val description: String
)

data class GroupedNotification(
    val date: String,
    val notifications: List<NotificationGet>
)