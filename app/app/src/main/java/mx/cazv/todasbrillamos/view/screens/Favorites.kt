package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.layouts.BasicLayout

@Composable
fun Favorites(navController: NavHostController) {
    BasicLayout(navController = navController) {
        Column {
            Text(text = "Favorites")
        }
    }
}