package mx.cazv.todasbrillamos.view.components.footer

import androidx.compose.runtime.Composable

/**
 * Barra inferior personalizada que toma un composable de contenido como parámetro.
 * @author Carlos Zamudio
 *
 * @param content El contenido que se mostrará dentro de la barra inferior personalizada.
 */
@Composable
fun CustomBottomBar(content: @Composable () -> Unit) {
    content()
}