package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.PostsViewModel

/**
 * Función que muestra la vista de un artículo con su título, autor, fecha de publicación
 * y contenido.
 * @author Min Che Kim
 *
 * @param navController El controlador de navegación de la aplicación.
 * @param authViewModel El ViewModel de autenticación.
 * @param postsViewModel El ViewModel de publicaciones.
 * @param postId El ID del artículo a mostrar.
 */
@Composable
fun PostView(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    postsViewModel: PostsViewModel,
    postId: Int
) {
    val postsState by postsViewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()
        if (token != null) {
            postsViewModel.getPostById(token, postId.toString())
        }
    }

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
        val post = postsState.posts.firstOrNull()

        if (post != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Left
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = "Escrito por: ${post.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = post.date,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(15.dp))

                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}