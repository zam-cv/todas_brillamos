package mx.cazv.todasbrillamos.view.components.header

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

/**
 * Barra superior personalizada que toma un composable de contenido como parámetro.
 * @author Carlos Zamudio
 *
 * @param content El contenido que se mostrará dentro de la barra superior personalizada.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(content: @Composable () -> Unit) {
    TopAppBar(
        title = {
            content()
        }
    )
}