package mx.cazv.todasbrillamos.model

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class Setting (
    val title: String,
    val icon: ImageVector,
    val route: String,
    val navController: NavController
)