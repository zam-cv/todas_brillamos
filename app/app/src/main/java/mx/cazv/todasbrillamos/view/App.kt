package mx.cazv.todasbrillamos.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.components.LoadingScreen
import mx.cazv.todasbrillamos.view.screens.Chat
import mx.cazv.todasbrillamos.view.screens.Favorites
import mx.cazv.todasbrillamos.view.screens.ForgotPassword
import mx.cazv.todasbrillamos.view.screens.home.Home
import mx.cazv.todasbrillamos.view.screens.Login
import mx.cazv.todasbrillamos.view.screens.Notifications
import mx.cazv.todasbrillamos.view.screens.Register
import mx.cazv.todasbrillamos.view.screens.Cart
import mx.cazv.todasbrillamos.view.screens.Orders
import mx.cazv.todasbrillamos.view.screens.Payment
import mx.cazv.todasbrillamos.view.screens.TrackOrder
import mx.cazv.todasbrillamos.view.screens.config.Config
import mx.cazv.todasbrillamos.view.screens.ProductDetails
import mx.cazv.todasbrillamos.view.screens.ShippingInfo
import mx.cazv.todasbrillamos.view.screens.Store
import mx.cazv.todasbrillamos.view.screens.calendar.Calendar
import mx.cazv.todasbrillamos.view.screens.calendar.YourCycle
import mx.cazv.todasbrillamos.view.screens.config.About
import mx.cazv.todasbrillamos.view.screens.config.ChangePassword
import mx.cazv.todasbrillamos.view.screens.config.EditProfile
import mx.cazv.todasbrillamos.view.screens.config.SocialNetworks
import mx.cazv.todasbrillamos.view.screens.config.TermsAndPolicies
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

@Composable
fun App() {
    val navController = rememberNavController()
    Nav(navController)
}

@Composable
fun Nav(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        startDestination = if (authViewModel.verify()) {
            Routes.ROUTE_HOME
        } else {
            Routes.ROUTE_LOGIN
        }
    }

    if (startDestination != null) {
        NavHost(navController = navController,
            startDestination = startDestination!!,
            modifier = modifier.fillMaxSize()
        ) {
            // Screens
            composable(Routes.ROUTE_LOGIN) {
                Login(navController, authViewModel)
            }

            composable(Routes.ROUTE_REGISTER) {
                Register(navController, authViewModel)
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

            composable(Routes.ROUTE_CHAT) {
                Chat(navController)
            }

            composable(Routes.ROUTE_FAVORITES) {
                Favorites(navController)
            }

            composable(Routes.ROUTE_NOTIFICATIONS) {
                Notifications(navController)
            }

            composable(Routes.ROUTE_CART) {
                Cart(navController)
            }

            composable(Routes.ROUTE_PRODUCT_DETAILS) {
                ProductDetails(navController)
            }

            composable(Routes.ROUTE_YOUR_CYCLE) {
                YourCycle(navController)
            }

            composable(Routes.ROUTE_TRACK_ORDER) {
                TrackOrder(navController)
            }

            composable(Routes.ROUTE_ORDERS) {
                Orders(navController)
            }

            // Config
            composable(Routes.ROUTE_CONFIG) {
                Config(navController, authViewModel)
            }

            composable(Routes.ROUTE_EDIT_PROFILE) {
                EditProfile(navController)
            }

            composable(Routes.ROUTE_CHANGE_PASSWORD) {
                ChangePassword(navController)
            }

            composable(Routes.ROUTE_SOCIAL_NETWORKS) {
                SocialNetworks(navController)
            }

            composable(Routes.ROUTE_TERMS_AND_POLICIES) {
                TermsAndPolicies(navController)
            }

            composable(Routes.ROUTE_ABOUT) {
                About(navController)
            }

            composable(Routes.ROUTE_SHIPPING_INFO){
                ShippingInfo(navController)
            }

            composable(Routes.ROUTE_PAYMENTS){
                Payment(navController)
            }
        }
    } else {
        LoadingScreen()
    }
}