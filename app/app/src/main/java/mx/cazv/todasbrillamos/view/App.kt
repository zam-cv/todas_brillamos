package mx.cazv.todasbrillamos.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.TopBar
import mx.cazv.todasbrillamos.view.screens.Chat
import mx.cazv.todasbrillamos.view.screens.Favorites
import mx.cazv.todasbrillamos.view.screens.ForgotPassword
import mx.cazv.todasbrillamos.view.screens.Home
import mx.cazv.todasbrillamos.view.screens.Login
import mx.cazv.todasbrillamos.view.screens.Notifications
import mx.cazv.todasbrillamos.view.screens.Register
import mx.cazv.todasbrillamos.view.screens.Calendar
//import mx.cazv.todasbrillamos.view.screens.Car
import mx.cazv.todasbrillamos.view.screens.Cart
import mx.cazv.todasbrillamos.view.screens.Config
import mx.cazv.todasbrillamos.view.screens.Store

@Composable
fun App() {
    val navController = rememberNavController()
    Nav(navController)
}

@Composable
fun Nav(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController,
        startDestination = Routes.ROUTE_LOGIN,
        modifier = modifier.fillMaxSize()) {

        // Screens
        composable(Routes.ROUTE_LOGIN) {
            Login(navController)
        }

        composable(Routes.ROUTE_REGISTER) {
            Register(navController)
        }

        composable(Routes.ROUTE_CART) {
            Cart(navController)
        }

        composable(Routes.ROUTE_FORGOT_PASSWORD) {
            ForgotPassword(navController)
        }

        composable(Routes.ROUTE_STORE) {
            Store(navController)
        }

        composable(Routes.ROUTE_CALENDAR) {
            Calendar(navController)
        }

        composable(Routes.ROUTE_HOME) {
            Home(navController)
        }

        composable(Routes.ROUTE_CONFIG) {
            Config(navController)
        }

        composable(Routes.ROUTE_CHAT) {
            Chat(navController)
        }

        composable(Routes.ROUTE_FAVORITES) {
            Favorites(navController)
        }

        composable(Routes.ROUTE_NOTIFICATIONS) {
            Notifications(navController)
        }

        /*
        composable(Routes.ROUTE_CART) {
            Cart(navController)
        }
         */
    }
}