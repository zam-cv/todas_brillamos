package mx.cazv.todasbrillamos.view.components.footer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.SelectedScreen
import mx.cazv.todasbrillamos.ui.theme.UnselectedScreen
import mx.cazv.todasbrillamos.view.Icon
import mx.cazv.todasbrillamos.view.Routes

/**
 * Archivo para la barra de navegación inferior.
 * @author Min Che Kim
 *  @author Carlos Zamudio
 */

/**
 * Función composable que muestra la barra de navegación inferior.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box {
        Box(
            modifier = Modifier.matchParentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bottom_bar_full),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        BottomAppBar(containerColor = Color.Transparent) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Routes.screens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navigateTo(screen.route, navController)
                            }
                        },
                        icon = { IconDisplay(screen, currentRoute) },
                        alwaysShowLabel = true,
                        colors = navBarItemsColors(),
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Navega a la ruta especificada utilizando el NavHostController proporcionado.
 *
 * @param route La ruta a la que se desea navegar.
 * @param navController El NavHostController utilizado para la navegación.
 */
fun navigateTo(route: String, navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    if (currentRoute != route) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    } else {
        Log.d("Navigation", "Attempted to navigate to current route: $route")
    }
}

/**
 * Proporciona los colores para los elementos de la barra de navegación.
 *
 * @return Los colores para los elementos de la barra de navegación.
 */
@Composable
fun navBarItemsColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = SelectedScreen,
    unselectedIconColor = UnselectedScreen,
    selectedTextColor = SelectedScreen,
    unselectedTextColor = UnselectedScreen,
    indicatorColor = Color.Transparent
)

/**
 * Muestra el icono para la pantalla dada.
 *
 * @param screen La pantalla para la cual se muestra el icono.
 * @param currentRoute La ruta actual.
 */
@Composable
fun IconDisplay(screen: Routes, currentRoute: String?) {
    val isSelected = screen.route == currentRoute
    val tintColor = if (isSelected) SelectedScreen else UnselectedScreen

    when (val iconType = screen.icon) {
        is Icon.VectorIcon -> {
            Icon(
                imageVector = iconType.imageVector,
                contentDescription = screen.tag,
                tint = tintColor
            )
        }
        is Icon.ResourceIcon -> {
            Icon(
                painter = painterResource(id = iconType.resId),
                contentDescription = screen.tag,
                tint = tintColor
            )
        }
    }
}