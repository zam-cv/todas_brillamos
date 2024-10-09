package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

/**
 * Función que muestra la vista de un artículo con su título, autor, fecha de publicación
 * y contenido.
 * @author Min Che Kim
 *
 * @param navController El controlador de navegación de la aplicación.
 * @param title El título del artículo.
 * @param author El autor del artículo.
 * @param date La fecha de publicación del artículo.
 * @param content El contenido del artículo.
 */
@Composable
fun PostView(
    navController: NavHostController,
    title: String = "Title",
    author: String = "Author",
    date: String = "Date",
    content: String = "Lorem ipsum dolor sit amet tema tis rolod muspi merol"
) {
    CustomLayout(
        navController = navController,
        withStoreButton = true,
        topBar = {
            BasicTopBar(title = "Artículo", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .padding(top = 20.dp)
        ) {
            Text(
                text = title, style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "escrito por: $author", style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }


}

/**
 * Vista previa de la pantalla de publicaciones
 */
@Preview(showBackground = true)
@Composable
fun PostPreview() {
    val navController = rememberNavController()
    PostView(
        title = "Title",
        author = "Author",
        date = "08/10/2024",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer pulvinar, purus et vestibulum scelerisque, purus mi euismod nibh, eget malesuada lorem nisi id purus. Donec vulputate lorem non ex sagittis, vel iaculis eros interdum. Aenean ultricies urna et tempor placerat. Vivamus egestas, lacus dapibus posuere viverra, urna est lobortis metus, nec fermentum turpis mauris ut leo. Cras fringilla scelerisque augue a hendrerit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla vel quam ac felis imperdiet ullamcorper. Sed ut accumsan ante. Fusce turpis turpis, varius ac odio vitae, vulputate imperdiet libero. Vivamus at augue consectetur ante fermentum dignissim. Nullam id euismod nibh.",
        navController = navController
    )

}
