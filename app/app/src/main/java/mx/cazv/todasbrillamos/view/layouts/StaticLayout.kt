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

/**
 * Layout estático que utiliza un Scaffold con una barra superior, una barra inferior
 * y un botón flotante de tienda.
 * @author Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param content El contenido que se mostrará dentro del layout.
 */
@Composable
fun StaticLayout(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold(
        topBar = { TopBar(navController) },
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
