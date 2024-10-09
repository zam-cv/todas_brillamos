package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.BasicLayout

/**
 * Pantalla de recuperación de contraseña que permite al usuario solicitar un enlace para restablecer su contraseña.
 * @author Jennyfer Jasso
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun ForgotPassword(navController: NavHostController) {
    BasicLayout(navController = navController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Box(
                modifier = Modifier
                    //.fillMaxHeight(0.25f)
                    //.background(Color.Transparent),
                    .fillMaxWidth()
                    .offset(y = (-200).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.degradado3),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillHeight)
//                Image(
//                    painter = painterResource(id = R.drawable.vector3),
//                    contentDescription = "Background",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .alpha(0.7f),
//                    alignment = Alignment.CenterEnd
//                )
//
//                Image(
//                    painter = painterResource(id = R.drawable.vector2),
//                    contentDescription = "Background",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .alpha(0.9f)
//                )
//
//                Image(
//                    painter = painterResource(id = R.drawable.vector1),
//                    contentDescription = "Background",
//                    modifier = Modifier.fillMaxSize()
//                    //alignment = Alignment.CenterEnd
//                )

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo_tb),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.Center)
                        .offset(y = 80.dp)
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                    //.offset(y = (-130).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "¿No recuerdas tu contraseña?",
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 35.sp)

                Spacer(modifier = Modifier.size(30.dp))

                Input(placeholder = "Correo electrónico")

                Spacer(modifier = Modifier.size(36.dp))

                Button(text = "Recuperar contraseña")

                Spacer(modifier = Modifier.size(6.dp))

                TextButton(onClick = { navController.navigate(Routes.ROUTE_LOGIN) }) {
                    Text(text = "Cancelar",
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontSize = 15.sp)
                }

            }
        }
    }
}

/**
 * Vista previa de la pantalla de recuperación de contraseña.
 */
@Preview
@Composable
fun FGPreview() {
    val navController = rememberNavController()
    ForgotPassword(navController)
}