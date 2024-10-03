package mx.cazv.todasbrillamos.view.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor

@Composable
fun BasicLayout(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold (
        containerColor = BackgroundColor,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)
            .background(BackgroundColor)) {
            content()
        }
    }
}