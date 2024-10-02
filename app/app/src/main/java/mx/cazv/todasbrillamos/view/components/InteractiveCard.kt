package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Función composable para crear una tarjeta interactiva las cual contiene un texto y una imagen.
 * @author
 *
 * @param text El texto que se mostrará en la tarjeta.
 * @param image El recurso de la imagen que se mostrará en la tarjeta.
 * @param modifier Modificador para personalizar la apariencia y el comportamiento de la tarjeta.
 * @param backgroundColor El color de fondo de la tarjeta.
 * @param textColor El color del texto en la tarjeta.
 * @param imageSize El tamaño de la imagen en la tarjeta.
 *
 */
@Composable
fun InteractiveCard(
    text: AnnotatedString,
    image: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color = Color.Black,
    imageSize: Int,
    imageAlignment: Alignment,
    valx: Dp = 0.dp,
    valy: Dp = 0.dp
    )
{
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
    ){
        Box(
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            //horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(
                painter = painterResource(id = image),
                contentDescription = text.toString(),
                modifier = Modifier
                    .size(imageSize.dp)
                    .align(imageAlignment)
                    .offset(x = valx, y = valy)
            )

            //Spacer(modifier = Modifier.height(50.dp))

            Text(text = text,
                color = textColor,
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
    }
}