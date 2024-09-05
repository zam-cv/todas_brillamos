package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.layouts.BasicLayout

@Composable
fun Login(navController: NavHostController) {
    BasicLayout(navController = navController) {
        Column {
            Text(text = "Login")
            TextButton(onClick = { navController.navigate(Routes.ROUTE_HOME) }) {
                Text(text = "Go to Home")
            }
            TextButton(onClick = { navController.navigate(Routes.ROUTE_REGISTER) }) {
                Text(text = "Go to Register")
            }

            TextButton(onClick = { navController.navigate(Routes.ROUTE_CART) }) {
                Text(text = "Go to Shopping Cart")
            }
        }
    }
}