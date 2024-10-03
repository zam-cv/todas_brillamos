package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun Login(navController: NavHostController) {
    BasicLayout(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Box(
                modifier = Modifier
                    //.fillMaxHeight(0.25f)
                    //.background(Color.Transparent)
                    .fillMaxWidth()
                    .offset(y = (-210).dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.degradado3),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillHeight)
                    //alignment = Alignment.Center,)
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
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .offset(y = (-130).dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Inicio de sesión",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
//                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.size(12.dp))

                TextButton(onClick = { navController.navigate(Routes.ROUTE_REGISTER) }) {
                    Row {
                        Text(text = "¿No tienes una cuenta? ")
                        Text(
                            text = "Regístrate",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.size(12.dp))

                Input(placeholder = "Correo electrónico")
                Spacer(modifier = Modifier.size(16.dp))

                Input(placeholder = "Contraseña",
                    suffixIcon = {
                        Icon(
                            Icons.Outlined.RemoveRedEye,
                            contentDescription = "Mostrar/Ocultar contraseña"
                        )
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Button(text = "Iniciar sesión")

                Spacer(modifier = Modifier.size(16.dp))

                TextButton(onClick = { navController.navigate(Routes.ROUTE_FORGOT_PASSWORD) },
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontSize = 15.sp
                    )
                }
                /**
                Text(text = "Login")
                TextButton(onClick = { navController.navigate(Routes.ROUTE_HOME) }) {
                Text(text = "Go to Home")
                }
                TextButton(onClick = { navController.navigate(Routes.ROUTE_REGISTER) }) {
                Text(text = "Go to Register")
                }

                TextButton(onClick = { navController.navigate(Routes.ROUTE_CART) }) {
                Text(text = "Go to Shopping Cart")
                }
                 */
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    Login(navController)
}