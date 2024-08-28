package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.layouts.BasicLayout

@Composable
fun ForgotPassword(navController: NavHostController) {
    BasicLayout(navController = navController) {
        Column {
            Text(text = "Forgot Password")
        }
    }
}