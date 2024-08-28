package mx.cazv.todasbrillamos.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes (
    val route: String,
    val tag: String,
    val icon: ImageVector
) {
    companion object {
        var screens = listOf(Login, Register)
        const val ROUTE_LOGIN = "Login"
        const val ROUTE_REGISTER = "Register"
    }

    private data object Login: Routes(ROUTE_LOGIN, "Login", Icons.Default.Home)
    private data object Register: Routes(ROUTE_REGISTER, "Register", Icons.Default.Person)
}