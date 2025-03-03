package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Line
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.view.openUrl

/**
 * Archivo para mostrar las redes sociales de la organización.
 * @author Carlos Zamudio
 */

/**
 * Composable que muestra un enlace a una red social con su nombre y logo.
 *
 * @param name El nombre de la red social.
 * @param without Indica si se debe mostrar una línea debajo del enlace.
 * @param image El recurso de imagen del logo de la red social.
 */
@Composable
fun Link(name: String, without: Boolean = false, image: Int, modifier: Modifier = Modifier) {
    Row (
        modifier = modifier
            .padding(bottom = 4.dp, top = 5.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Logo",
            modifier = modifier
                .width(30.dp)
                .height(30.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(end = 10.dp)
                .fillMaxWidth()
        )
    }

    if (!without) {
        Line(height = 0.5.dp)
    }
}

/**
 * Pantalla de redes sociales que muestra los enlaces a las redes sociales de la organización.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun SocialNetworks(navController: NavHostController) {
    val context = LocalContext.current

    CustomLayout(
        navController = navController,
        withStoreButton = true,
        topBar = {
            BasicTopBar(title = "Redes sociales", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(top = 40.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TODAS BRILLAMOS",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(bottom = 10.dp)
            ) {
                Link(
                    name = "fundaciontodasbrillamos",
                    image = R.drawable.instagram,
                    modifier = Modifier
                        .clickable {
                            openUrl(context, "https://www.instagram.com/fundaciontodasbrillamos/")
                        }
                )

                Link(
                    name = "Todas Brillamos",
                    image = R.drawable.facebook,
                    modifier = Modifier
                        .clickable {
                            openUrl(context, "https://www.facebook.com/FundacionTodasBrillamos")
                        }

                )

                Link(
                    name = "todas.brillamos",
                    image = R.drawable.tiktok,
                    modifier = Modifier
                        .clickable {
                            openUrl(context, "https://www.tiktok.com/@todas.brillamos")
                        }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ZAZIL",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.zazil),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(bottom = 10.dp)
            ) {
                Link(
                    name = "toallas.zazil",
                    image = R.drawable.instagram,
                    modifier = Modifier
                        .clickable {
                            openUrl(context, "https://www.instagram.com/toallas.zazil/")
                        }
                )

                Link(
                    name = "Toallas.Zazil",
                    image = R.drawable.facebook,
                    modifier = Modifier
                        .clickable {
                            openUrl(context, "https://www.facebook.com/people/ToallasZazil/100094194291246/")
                        }
                )
            }
        }
    }
}