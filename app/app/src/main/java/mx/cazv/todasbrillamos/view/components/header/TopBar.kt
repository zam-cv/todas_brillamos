package mx.cazv.todasbrillamos.view.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes

/**
 * Barra superior con iconos de notificaciones, pedidos, favoritos y carrito de compras.
 * @author Carlos Zamudio
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun TopBar(navController: NavHostController) {
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
            IconButton(
                onClick = { navController.navigate(Routes.ROUTE_NOTIFICATIONS) },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications"
                )
            }

            Text(
                text = "ZAZIL",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(-8.dp)
            ) {
                IconButton(onClick = { navController.navigate(Routes.ROUTE_ORDERS) }) {
                    Icon(
                        imageVector = Icons.Outlined.LocalShipping,
                        contentDescription = "Orders"
                    )
                }
                IconButton(onClick = { navController.navigate(Routes.ROUTE_FAVORITES) }) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorites"
                    )
                }
                IconButton(onClick = { navController.navigate(Routes.ROUTE_CART) }) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Shopping Cart"
                    )
                }
            }
        }
    }
}