package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.view.screens.store.CategoryFilter
import mx.cazv.todasbrillamos.view.screens.store.ToggleView
import mx.cazv.todasbrillamos.view.screens.store.ViewFavorites
import mx.cazv.todasbrillamos.view.screens.store.ViewProducts

@Composable
fun Favorites(navController: NavHostController) {
    MainLayout(navController = navController) {
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