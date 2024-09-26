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
        const val ROUTE_CART = "Cart"
        const val ROUTE_PRODUCT_DETAILS = "ProductDetails"
        const val ROUTE_YOUR_CYCLE = "YourCycle"
        const val ROUTE_TRACK_ORDER = "TrackOrder"
        const val ROUTE_SHIPPING_INFO = "ShippingInfo"
        const val ROUTE_PAYMENTS = "Payments"

        // Config
        const val ROUTE_CONFIG = "Config"
        const val ROUTE_EDIT_PROFILE = "EditProfile"
        const val ROUTE_CHANGE_PASSWORD = "ChangePassword"
        const val ROUTE_SOCIAL_NETWORKS = "SocialNetworks"
        const val ROUTE_TERMS_AND_POLICIES = "TermsAndPolicies"
        const val ROUTE_ABOUT = "About"

    }

    // Bottom bar routes
    private data object Store: Routes(ROUTE_STORE, "Store", Icons.Default.Person)
    private data object Home: Routes(ROUTE_HOME, "Home", Icons.Default.Home)
    private data object Calendar: Routes(ROUTE_CALENDAR, "Calendar", Icons.Default.Person)
    private data object Chat: Routes(ROUTE_CHAT, "Chat", Icons.Default.Person)
    private data object Config: Routes(ROUTE_CONFIG, "Config", Icons.Default.Person)
    private data object Cart: Routes(ROUTE_CART, "Cart", Icons.Default.ShoppingCart)

}