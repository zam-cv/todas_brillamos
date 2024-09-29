package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.ui.theme.ButtonColor

@Composable
fun CustomButton(text: String, col: Color, colT: Color = Color.White,imageId: Int? = null){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(col, shape = RoundedCornerShape(10.dp))
    ) {
        OutlinedButton(
            onClick = {},
            border = null,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),

        ) {
            Row() {
                if (imageId != null) {
                    val image = painterResource(id = imageId)
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre la imagen y el texto
                }

                // Mostrar el texto del botón
                Text(
                    text = text,
                    color = colT,
                    fontWeight = FontWeight.W400,
                    fontSize = 17.sp
                )
            }
        }
    }
}