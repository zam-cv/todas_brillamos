package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.BadgeColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Line
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
//import mx.cazv.todasbrillamos.viewmodel.NotificationViewModel
import mx.cazv.todasbrillamos.viewmodel.NotificationsViewModel
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

/**
 * Archivo para mostrar notificaciones.
 * @author Carlos Zamudio
 */

/**
 * Composable que muestra una fecha con líneas a los lados.
 *
 * @param value La fecha a mostrar.
 */
@Composable
fun Date(value: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 13.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Line()
        }

        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BadgeColor)
                .padding(start = 10.dp, end = 10.dp)
                .wrapContentWidth()

        ) {
            Text(text = value, fontSize = 14.sp)
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Line()
        }
    }
}

/**
 * Composable que muestra una notificación con un título, descripción y hora.
 *
 * @param title El título de la notificación.
 * @param description La descripción de la notificación.
 * @param time La hora de la notificación.
 */
@Composable
fun Notification(
    title: String,
    description: String,
    hour: String,
    withLine: Boolean = true
) {
    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, end = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 10.dp, end = 13.dp)
            ) {
                Icon(
                    Icons.Outlined.NotificationsActive,
                    contentDescription = "Notification",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }

            Column {

                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                    ) {
                        Text(hour,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                }

                Text(description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        if (withLine) {
            Box(modifier = Modifier.padding(top = 15.dp)) {
                Line(height = 0.5.dp)
            }
        }
    }
}

/**
 * Pantalla de notificaciones que muestra una lista de notificaciones agrupadas por fecha.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun Notifications(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notificationsViewModel: NotificationsViewModel
) {
    val notifications = notificationsViewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            notificationsViewModel.loadNotifications(token)
        }
    }

    CustomLayout (
        withStoreButton = true,
        navController = rememberNavController(),
        topBar = {
            BasicTopBar(title = "Notificaciones", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 25.dp)
        ) {
            notifications.value.forEach { group ->
                Date(group.date)

                group.notifications.forEach { notification ->
                    Notification(
                        title = notification.title,
                        description = notification.description,
                        hour = notification.hour,
                        withLine = group.notifications.last() != notification
                    )
                }
            }
        }
    }
}