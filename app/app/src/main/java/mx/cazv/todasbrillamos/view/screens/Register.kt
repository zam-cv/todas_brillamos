package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Checkbox
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor


@Composable
fun Register(navController: NavHostController) {
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
                Image(
                    painter = painterResource(id = R.drawable.degradado3),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillHeight
                )
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
                    .padding(start = 10.dp, end = 10.dp)
                    .offset(y = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.size(16.dp))

                // Título de registrate
                Text(
                    text = "¡Regístrate!",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Input(placeholder = "Nombre")
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Input(placeholder = "Apellido")
                    }
                }


                Spacer(modifier = Modifier.size(16.dp))

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

                Input(placeholder = "Confirmar contraseña",
                    suffixIcon = {
                        Icon(
                            Icons.Outlined.RemoveRedEye,
                            contentDescription = "Mostrar/Ocultar contraseña"
                        )
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { }
                    )
                    Text(
                        text = "Me gustaría recibir información de Todas Brillamos por correo",
                        fontSize = 14.sp,
                        style = TextStyle(lineHeight = 17.sp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = { }
                    )
                    Text(
                        text = "Acepto el aviso de privacidad",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Button(text = "Continuar")
                Spacer(modifier = Modifier.size(16.dp))

                TextButton(onClick = { navController.navigate(Routes.ROUTE_LOGIN) }) {
                    Text(
                        text = "Ya tengo una cuenta",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    val navController = rememberNavController()
    Register(navController)
}

