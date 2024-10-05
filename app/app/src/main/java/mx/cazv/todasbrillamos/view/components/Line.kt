package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Función composable que dibuja una línea horizontal.
 * @author Carlos Zamudio
 *
 * @param height La altura de la línea.
 * @param color El color de la línea.
 */
@Composable
fun Line(height: Dp = 1.5.dp, color: Color = Color.Gray) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(height)
            .background(color)
    )
}