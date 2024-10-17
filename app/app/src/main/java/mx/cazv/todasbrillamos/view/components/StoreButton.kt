package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.SelectedScreen
import mx.cazv.todasbrillamos.view.Routes.Companion.ROUTE_STORE

/**
 * Función composable que muestra un botón flotante para navegar a la tienda.
 * @author Min Che Kim
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param modifier Modificador para personalizar la apariencia y el comportamiento del botón.
 */
@Composable
fun StoreButton(navController: NavHostController, modifier: Modifier = Modifier) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isInStoreScreen = currentBackStackEntry?.destination?.route == ROUTE_STORE

    Box(modifier = modifier
        .size(90.dp)
        .offset(y = 80.dp)
        .padding(4.dp)
    ) {
        if (isInStoreScreen) {
            Image(
                painter = painterResource(id = R.drawable.store_selected),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        FloatingActionButton(
            onClick = {
                if (!isInStoreScreen) {
                    navController.navigate(ROUTE_STORE) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            shape = CircleShape,
            containerColor = Color(0xFFE7F4FA),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isInStoreScreen) 6.dp else 0.dp)
                .border(
                    width = 6.dp,
                    color = if (isInStoreScreen) Color.Transparent else BackgroundColor,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.store_ic),
                contentDescription = "tienda",
                modifier = Modifier.size(32.dp),
                tint = SelectedScreen
            )
        }
    }
}