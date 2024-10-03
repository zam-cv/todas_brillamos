package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.layouts.StaticLayout
import mx.cazv.todasbrillamos.view.screens.store.ViewFavorites

@Composable
fun Favorites(navController: NavHostController) {
    StaticLayout(navController = navController) {
        Column (modifier = Modifier.fillMaxWidth()){
            Text(text = "Favorites")
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