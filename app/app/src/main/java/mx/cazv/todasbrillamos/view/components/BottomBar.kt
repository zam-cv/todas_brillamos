package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.SelectedScreen
import mx.cazv.todasbrillamos.ui.theme.UnselectedScreen
import mx.cazv.todasbrillamos.view.Icon
import mx.cazv.todasbrillamos.view.Routes

@Composable
fun BottomBar(navController: NavHostController) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bottom_bar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        BottomAppBar (containerColor = Color.Transparent) {
            val stack by navController.currentBackStackEntryAsState()
            val currentPage = stack?.destination

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Routes.screens.take(2).forEach { screen ->
                    NavigationBarItem(
                        selected = screen.route == currentPage?.route,
                        onClick = { navigateTo(screen.route, navController) },
                        // label = { Text(text = screen.tag) },
                        icon = { IconDisplay(screen, currentPage) },
                        alwaysShowLabel = true,
                        colors = navBarItemsColors(),
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Routes.screens.drop(2).take(2).forEach { screen ->
                    NavigationBarItem(
                        selected = screen.route == currentPage?.route,
                        onClick = { navigateTo(screen.route, navController) },
                        // label = { Text(text = screen.tag) },
                        icon = { IconDisplay(screen, currentPage) },
                        alwaysShowLabel = true,
                        colors = navBarItemsColors(),
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )
                }
            }
        }
    }
}

fun navigateTo(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun navBarItemsColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = SelectedScreen, // Color del ícono cuando está seleccionado
    unselectedIconColor = UnselectedScreen, // Color del ícono cuando no está seleccionado
    selectedTextColor = SelectedScreen, // Color del texto cuando está seleccionado
    unselectedTextColor = UnselectedScreen, // Color del texto cuando no está seleccionado
    indicatorColor = Color.Transparent // Color transparente para eliminar la sombra gris
)

@Composable
fun IconDisplay(screen: Routes, currentPage: NavDestination?) {
    when (val iconType = screen.icon) {
        is Icon.VectorIcon -> {
            Icon(
                imageVector = iconType.imageVector,
                contentDescription = screen.tag,
                tint = if (screen.route == currentPage?.route) SelectedScreen else UnselectedScreen,
//                modifier = Modifier
//                    .padding(bottom = 8.dp)
//                    .offset(y = 4.dp)
            )
        }

        is Icon.ResourceIcon -> {
            Icon(
                painter = painterResource(id = iconType.resId),
                contentDescription = screen.tag,
                tint = if (screen.route == currentPage?.route) SelectedScreen else UnselectedScreen
            )
        }
    }
}