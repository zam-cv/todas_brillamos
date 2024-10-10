package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.view.screens.store.ViewFavorites
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.FavoritesViewModel

/**
 * Pantalla de favoritos que muestra una lista de productos favoritos del usuario.
 * @author Min Che Kim, Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El AuthViewModel utilizado para obtener el token del usuario.
 * @param favoritesViewModel El FavoritesViewModel utilizado para obtener los productos favoritos del usuario.
 */
@Composable
fun Favorites(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    CustomLayout (
        withStoreButton = true,
        withScroll = false,
        navController = navController,
        topBar = {
            BasicTopBar(title = "Mis Favoritos", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column (modifier = Modifier.fillMaxWidth()){
            ViewFavorites(authViewModel, favoritesViewModel, navController)
        }
    }
}