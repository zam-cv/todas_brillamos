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

@Composable
fun Favorites(navController: NavHostController) {
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
            ViewFavorites()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesPreview() {
    val navController = rememberNavController()
    Favorites(navController = navController)
}