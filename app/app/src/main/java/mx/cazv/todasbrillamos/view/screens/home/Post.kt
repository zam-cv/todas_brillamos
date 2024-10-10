package mx.cazv.todasbrillamos.view.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes

/**
 * Composable que muestra una publicación con un título, contenido y un botón para leer más.
 * @author Jennyfer Jasso, Carlos Zamudio
 *
 * @param navController El controlador de navegación.
 * @param postId El ID de la publicación.
 * @param title El título de la publicación.
 * @param content El contenido de la publicación.
 * @param wordLimit El límite de palabras a mostrar antes de truncar el contenido.
 */
@Composable
fun Post(navController: NavHostController, postId: Int, title: String, content: String, wordLimit: Int = 20) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xfff4d0cb)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = limitWords(content, wordLimit),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextButton(
                onClick = { navController.navigate(Routes.ROUTE_POST + "/$postId") },
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Leer más",
                        textDecoration = TextDecoration.Underline,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

/**
 * Función que limita el número de palabras en un texto.
 * @author Carlos Zamudio
 *
 * @param text El texto a truncar.
 * @param limit El número máximo de palabras a mostrar.
 * @return El texto truncado con un sufijo "..." si excede el límite de palabras.
 */
fun limitWords(text: String, limit: Int): String {
    val words = text.split(" ")
    return if (words.size > limit) {
        words.take(limit).joinToString(" ") + "..."
    } else {
        text
    }
}