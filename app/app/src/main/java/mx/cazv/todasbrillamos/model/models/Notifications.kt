package mx.cazv.todasbrillamos.model.models

import java.sql.Time

//data class NotificationGet(
//    val hour: String,
//    val title: String,
//    val description: String,
//    val clientId: Int
//)
//
//data class GroupedNotification(
//    val date: String,
//    val notificationGet: NotificationGet = NotificationGet(),
//)

data class NotificationGet(
    val hour: String,
    val title: String,
    val description: String,
    val clientId: Int
)

data class GroupedNotification(
    val date: String,
    val notifications: List<NotificationGet>
)