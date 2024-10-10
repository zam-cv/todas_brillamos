package mx.cazv.todasbrillamos.view.components.footer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.R

/**
 * Archivo para el botón de la barra de navegación inferior.
 *
 * @author Carlos Zamudio
 */

/**
 * Función composable que muestra una barra inferior con un botón.
 *
 * @param buttonText El texto que se mostrará en el botón.
 * @param onClick La acción que se ejecutará al hacer clic en el botón.
 */
@Composable
fun ButtonBottomBar(buttonText: String, onClick: () -> Unit, barImage: Int = R.drawable.bottom_bar_full) {
    Box {
        Box(
            modifier = Modifier.matchParentSize()
        ) {
            Image(
                painter = painterResource(id = barImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        BottomAppBar(
            containerColor = Color.Transparent,
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        text = buttonText,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        modifier = Modifier
                            .padding(top = 7.dp, bottom = 15.dp)
                    )
                }
            }
        }
    }
}