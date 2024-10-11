package mx.cazv.todasbrillamos.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector
import mx.cazv.todasbrillamos.R

/**
 * Archivo para definir las rutas de las pantallas de la aplicación.
 * @author Carlos Zamudio, Min Che Kim, Jennyfer Jasso, Mariana Balderrábano
 *
 */

/**
 * Ruta de las pantallas de la aplicación.
 * Define las rutas y los íconos asociados a cada pantalla.
 * @param route La ruta de la pantalla.
 * @param tag La etiqueta de la pantalla.
 * @param icon El ícono asociado a la pantalla.
 */
sealed class Routes (
    val route: String,
    val tag: String,
    val icon: Icon
) {
    companion object {
        var screens = listOf(Home, Calendar, Store, Chat, Config)

        const val ROUTE_LOGIN = "Login"
        const val ROUTE_REGISTER = "Register"
        const val ROUTE_TERMS_AND_POLICIES_REGISTER = "TermsAndPoliciesRegister"

        const val ROUTE_STORE = "Store"
        const val ROUTE_HOME = "Home"
        const val ROUTE_POST = "Post"
        const val ROUTE_CALENDAR = "Calendar"
        const val ROUTE_FORGOT_PASSWORD = "ForgotPassword"
        const val ROUTE_CHAT = "Chat"
        const val ROUTE_FAVORITES = "Favorites"
        const val ROUTE_NOTIFICATIONS = "Notifications"
        const val ROUTE_CART = "Cart"
        const val ROUTE_PRODUCT_DETAILS = "ProductDetails"
        const val ROUTE_YOUR_CYCLE = "YourCycle"
        const val ROUTE_ORDERS = "Orders"
        const val ROUTE_TRACK_ORDER = "TrackOrder"
        const val ROUTE_SHIPPING_INFO = "ShippingInfo"

        // Config
        const val ROUTE_CONFIG = "Config"
        const val ROUTE_EDIT_PROFILE = "EditProfile"
        const val ROUTE_CHANGE_PASSWORD = "ChangePassword"
        const val ROUTE_SOCIAL_NETWORKS = "SocialNetworks"
        const val ROUTE_TERMS_AND_POLICIES = "TermsAndPolicies"
        const val ROUTE_ABOUT = "About"
    }

    // Bottom bar routes
    private data object Home: Routes(ROUTE_HOME, "Inicio", Icon.ResourceIcon(R.drawable.home_icon))
    private data object Calendar: Routes(ROUTE_CALENDAR, "Calendario", Icon.VectorIcon(Icons.Default.CalendarToday))
    private data object Store: Routes(ROUTE_STORE, "Store", Icon.VectorIcon(Icons.Default.Store))
    private data object Chat: Routes(ROUTE_CHAT, "Chat", Icon.ResourceIcon(R.drawable.chat_icon))
    private data object Config: Routes(ROUTE_CONFIG, "Config", Icon.VectorIcon(Icons.Default.Settings))
}

/**
 * Clase sellada que representa un ícono.
 */
sealed class Icon {
    data class VectorIcon(val imageVector: ImageVector) : Icon()
    data class ResourceIcon(val resId: Int) : Icon()
}
