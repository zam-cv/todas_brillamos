package mx.cazv.todasbrillamos.view.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.NotificationsViewModel

/**
 * Barra superior con iconos de notificaciones, pedidos, favoritos y carrito de compras.
 * @author Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun TopBar(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notificationsViewModel: NotificationsViewModel,
) {
    var unreadCount by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()
        if (token != null) {
            notificationsViewModel.getUnread(token).let { unreadCount = it }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier.align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { navController.navigate(Routes.ROUTE_NOTIFICATIONS) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }}) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications"
                    )
                }
                if (unreadCount > 0) {
                    NotificationBadge(count = unreadCount)
                }
            }

            Text(
                text = "ZAZIL",
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(-8.dp)
            ) {
                IconButton(onClick = { navController.navigate(Routes.ROUTE_ORDERS) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                } }) {
                    Icon(
                        imageVector = Icons.Outlined.LocalShipping,
                        contentDescription = "Orders"
                    )
                }
                IconButton(onClick = { navController.navigate(Routes.ROUTE_FAVORITES) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
                }) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorites"
                    )
                }
                IconButton(onClick = { navController.navigate(Routes.ROUTE_CART) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }}) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Shopping Cart"
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(19.dp)
            .offset(x = 12.dp, y = (-8).dp)
            .clip(CircleShape)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
        )
    }
}