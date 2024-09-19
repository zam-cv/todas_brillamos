package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Nav
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.TopBar
import mx.cazv.todasbrillamos.view.layouts.MainLayout

@Composable
fun Home(navController: NavHostController) {
    MainLayout(navController = navController) {
        Text(text = "Home")
    }
}

