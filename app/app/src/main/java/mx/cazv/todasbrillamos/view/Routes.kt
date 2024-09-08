package mx.cazv.todasbrillamos.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes (
    val route: String,
    val tag: String,
    val icon: ImageVector
) {
    companion object {
        var screens = listOf(Home, Calendar, Store, Chat, Config, Cart)
        const val ROUTE_LOGIN = "Login"
        const val ROUTE_REGISTER = "Register"
        const val ROUTE_STORE = "Store"
        const val ROUTE_HOME = "Home"
        const val ROUTE_CALENDAR = "Calendar"
        const val ROUTE_FORGOT_PASSWORD = "ForgotPassword"
        const val ROUTE_CHAT = "Chat"
        const val ROUTE_FAVORITES = "Favorites"
        const val ROUTE_NOTIFICATIONS = "Notifications"
        const val ROUTE_CONFIG = "Config"
        const val ROUTE_CART = "Cart"
        const val ROUTE_PRODUCT_DETAILS = "ProductDetails"
    }

    private data object Login: Routes(ROUTE_LOGIN, "Login", Icons.Default.Home)
    private data object Register: Routes(ROUTE_REGISTER, "Register", Icons.Default.Person)
    private data object Store: Routes(ROUTE_STORE, "Store", Icons.Default.Person)
    private data object Home: Routes(ROUTE_HOME, "Home", Icons.Default.Home)
    private data object Calendar: Routes(ROUTE_CALENDAR, "Calendar", Icons.Default.Person)
    private data object ForgotPassword: Routes(ROUTE_FORGOT_PASSWORD, "ForgotPassword", Icons.Default.Person)
    private data object Chat: Routes(ROUTE_CHAT, "Chat", Icons.Default.Person)
    private data object Favorites: Routes(ROUTE_FAVORITES, "Favorites", Icons.Default.Person)
    private data object Notifications: Routes(ROUTE_NOTIFICATIONS, "Notifications", Icons.Default.Person)
    private data object Config: Routes(ROUTE_CONFIG, "Config", Icons.Default.Person)
    private data object Cart: Routes(ROUTE_CART, "Cart", Icons.Default.ShoppingCart)
    private data object ProductDetails: Routes(ROUTE_PRODUCT_DETAILS, "ProductDetails", Icons.Default.Person)
}