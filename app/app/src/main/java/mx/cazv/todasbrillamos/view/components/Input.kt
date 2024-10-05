package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.ui.theme.Stroke

/**
 * Función composable que muestra un campo de entrada de texto con opciones de ícono y estilo.
 * @author Carlos Zamudio
 *
 * @param placeholder El texto de marcador de posición que se mostrará cuando el campo esté vacío.
 * @param suffixIcon Un composable opcional que se mostrará al final del campo de entrada.
 * @param imageId El recurso de la imagen que se mostrará en el campo de entrada (opcional).
 * @param height La altura del campo de entrada (opcional).
 * @param padding El relleno interno del campo de entrada.
 * @param imageSize El tamaño de la imagen en el campo de entrada.
 * @param topStart El radio de la esquina superior izquierda del campo de entrada.
 * @param topEnd El radio de la esquina superior derecha del campo de entrada.
 * @param bottomEnd El radio de la esquina inferior derecha del campo de entrada.
 * @param bottomStart El radio de la esquina inferior izquierda del campo de entrada.
 * @param value El valor actual del campo de entrada.
 * @param onValueChange La función que se llamará cuando el valor del campo de entrada cambie.
 */
@Composable
fun Input(
    placeholder: String,
    suffixIcon : @Composable (() -> Unit)? = null,
    imageId: Int? = null,
    height: Int? = null,
    padding: Dp = 20.dp,
    imageSize: Dp = 24.dp,
    topStart: Dp = 10.dp,
    topEnd: Dp = 10.dp,
    bottomEnd: Dp = 10.dp,
    bottomStart: Dp = 10.dp,
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    val shape = RoundedCornerShape(topStart,topEnd,bottomEnd,bottomStart)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .let { modifier ->
                if (height != null) {
                    modifier.height(height.dp)
                } else {
                    modifier
                }
            }
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Stroke,
                shape = shape
            )
            .padding(start = 10.dp, end = 10.dp, bottom = padding, top = padding),

        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    } else {
                        innerTextField()
                    }
                }

                if (suffixIcon != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 3.dp)
                    ) {
                        suffixIcon()
                    }
                }
                if (imageId != null) {
                    val image = painterResource(id = imageId)
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(imageSize))
                }
            }
        }
    )
}