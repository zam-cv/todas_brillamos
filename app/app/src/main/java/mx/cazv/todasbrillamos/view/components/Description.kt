package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Función composable que muestra una descripción con un punto de viñeta.
 * @author Carlos Zamudio
 *
 * @param text El texto de la descripción.
 */
@Composable
fun Description(text: String) {
    Row {
        Text(
            text = "•",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp, end = 10.dp)
        )

        Text(
            text = text,
            fontSize = 13.sp,
        )
    }
}