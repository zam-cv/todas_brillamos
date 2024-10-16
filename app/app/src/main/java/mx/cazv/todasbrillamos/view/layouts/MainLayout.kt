package mx.cazv.todasbrillamos.view.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.StoreButton
import mx.cazv.todasbrillamos.view.components.header.TopBar
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.NotificationsViewModel

/**
 * Layout principal que utiliza un Scaffold con una barra superior, una barra inferior,
 * un botón flotante de tienda y desplazamiento vertical.
 * @author Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param content El contenido que se mostrará dentro del layout.
 */
@Composable
fun MainLayout(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notificationsViewModel: NotificationsViewModel,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopBar(navController, authViewModel, notificationsViewModel) },
        bottomBar = { BottomBar(navController) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { StoreButton(navController) },
        containerColor = BackgroundColor,
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            ) {
            content()
        }
    }
}
