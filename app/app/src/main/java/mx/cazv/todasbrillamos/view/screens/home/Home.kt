package mx.cazv.todasbrillamos.view.screens.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.states.RandomState
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.view.openUrl
import mx.cazv.todasbrillamos.view.screens.MoreProducts
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.NotificationsViewModel
import mx.cazv.todasbrillamos.viewmodel.PostsViewModel
import mx.cazv.todasbrillamos.viewmodel.RandomViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Pantalla principal que muestra un saludo, tarjetas interactivas y productos recomendados.
 * @author Jennyfer Jasso, Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación utilizado para obtener el token del usuario.
 * @param userViewModel El ViewModel de usuario utilizado para cargar la información del usuario.
 * @param postsViewModel El ViewModel de publicaciones utilizado para cargar las publicaciones.
 * @param randomState El estado del ViewModel de productos aleatorios utilizado para mostrar los productos recomendados.
 */
@Composable
fun Home(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    postsViewModel: PostsViewModel,
    notificationsViewModel: NotificationsViewModel,
    randomState: State<RandomState>
) {
    val userState = userViewModel.state.collectAsState()
    val postsState = postsViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            userViewModel.loadUserInfo(token)
            postsViewModel.loadPosts(token)
        }
    }

    MainLayout(navController = navController, authViewModel, notificationsViewModel) {
        Column(
            modifier = Modifier
                .padding(top = 75.dp, start = 15.dp, end = 15.dp, bottom = 25.dp)
        ) {
            GreetingSec(userState.value.details.first_name + " " + userState.value.details.last_name)
            Spacer(modifier = Modifier.height(30.dp))
            InteractiveCardsHome(navController)

            if (randomState.value.products.products.isNotEmpty()) {
                MoreProducts(
                    text = "Recomendado",
                    products = randomState.value.products,
                    navController = navController,
                    modifier = Modifier.padding(top = 40.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
            else {
                Spacer(modifier = Modifier.height(60.dp))
            }


            Box {
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Descubre y aprende...",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 10.dp))

                    Row (horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()){
                        Image(
                            painter = painterResource(id = R.drawable.instagram),
                            contentDescription = "Instagram",
                            modifier = Modifier.size(30.dp)
                                .clickable{
                                    openUrl(context, "https://www.instagram.com/toallas.zazil/")
                                }
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Image(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Facebook",
                            modifier = Modifier.size(30.dp)
                                .clickable{
                                    openUrl(context, "https://www.facebook.com/people/ToallasZazil/100094194291246/")
                                }
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (postsState.value.posts.isNotEmpty()) {
                for (post in postsState.value.posts) {
                    Post(navController, post.id, post.title, post.content)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            else {
                Text(text = "Todavía no hay publicaciones para mostrar.")
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}



