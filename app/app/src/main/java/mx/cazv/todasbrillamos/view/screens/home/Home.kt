package mx.cazv.todasbrillamos.view.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.model.states.RandomState
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.view.screens.MoreProducts
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.PostsViewModel
import mx.cazv.todasbrillamos.viewmodel.RandomViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Pantalla principal que muestra un saludo, tarjetas interactivas y productos recomendados.
 * @author Jennyfer Jasso
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación utilizado para obtener el token del usuario.
 * @param userViewModel El ViewModel de usuario utilizado para cargar la información del usuario.
 * @param randomViewModel El ViewModel de productos aleatorios utilizado para cargar productos recomendados.
 * @param postsViewModel El ViewModel de publicaciones utilizado para cargar publicaciones.
 */
@Composable
fun Home(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    postsViewModel: PostsViewModel,
    randomState: State<RandomState>
) {
    val userState = userViewModel.state.collectAsState()
    val postsState = postsViewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            userViewModel.loadUserInfo(token)
            postsViewModel.loadPosts(token)
        }
    }

    MainLayout(navController = navController) {
        Column(
            modifier = Modifier
                .padding(top = 75.dp, start = 15.dp, end = 15.dp, bottom = 25.dp)
        ) {
            GreetingSec(userState.value.details.first_name + " " + userState.value.details.last_name)
            Spacer(modifier = Modifier.height(30.dp))
            InteractiveCardsHome()

            if (randomState.value.products.products.isNotEmpty()) {
                MoreProducts(
                    text = "Recomendado",
                    products = randomState.value.products,
                    navController = navController,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (postsState.value.posts.isNotEmpty()) {
                for (post in postsState.value.posts) {
                    Post(navController, post.title, post.content)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

