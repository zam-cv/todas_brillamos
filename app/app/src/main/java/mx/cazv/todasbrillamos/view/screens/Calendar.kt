package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.view.layouts.MainLayout

@Composable
fun Calendar(navController: NavHostController) {
    MainLayout(navController = navController) {
        Column {
            Text(text = "Calendar")
        }
    }
}