package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.cazv.todasbrillamos.view.Routes

@Composable
fun BottomBar(navController: NavHostController) {
    BottomAppBar {
        val stack by navController.currentBackStackEntryAsState()
        val currentPage = stack?.destination

        Routes.screens.forEach { screen ->
            NavigationBarItem(
                selected = screen.route == currentPage?.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                label = { Text(text = screen.tag) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.tag
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}