package mx.cazv.todasbrillamos.view.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.StoreButton
import mx.cazv.todasbrillamos.view.components.header.TopBar

@Composable
fun StaticLayout(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { StoreButton(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(BackgroundColor)
        ) {
            content()
        }
    }
}
