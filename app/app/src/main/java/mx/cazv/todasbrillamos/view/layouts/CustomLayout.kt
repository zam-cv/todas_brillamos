package mx.cazv.todasbrillamos.view.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.StoreButton

/**
 * Layout personalizado que utiliza un Scaffold con opciones para una barra superior, una barra inferior,
 * un botón flotante de tienda y desplazamiento vertical.
 * @author Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param topBar El contenido de la barra superior.
 * @param bottomBar El contenido de la barra inferior.
 * @param withStoreButton Indica si se debe mostrar el botón flotante de tienda.
 * @param withScroll Indica si el contenido debe ser desplazable verticalmente.
 * @param content El contenido que se mostrará dentro del layout.
 */
@Composable
fun CustomLayout(
    navController: NavHostController,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    withStoreButton: Boolean = false,
    withScroll: Boolean = true,
    content: @Composable () -> Unit
) {
    Scaffold (
        topBar = { topBar() },
        bottomBar = { bottomBar() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { if (withStoreButton) StoreButton(navController) },
        containerColor = BackgroundColor,
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
            .background(BackgroundColor)
    ) { innerPadding ->
        if (withScroll) {
            Box(modifier = Modifier
                .padding(innerPadding)
                .background(BackgroundColor)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(BackgroundColor)
            ) {
                content()
            }
        } else {
            Box(modifier = Modifier
                .padding(innerPadding)
                .background(BackgroundColor)
                .fillMaxSize()
                .background(BackgroundColor)
            ) {
                content()
            }
        }
    }
}