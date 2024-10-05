package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.viewmodel.AuthState
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

/**
 * Pantalla de inicio de sesión que permite al usuario ingresar su correo electrónico y contraseña para iniciar sesión.
 * @author Jennyfer Jasso
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param viewModel El ViewModel de autenticación utilizado para gestionar el inicio de sesión del usuario.
 */
@Composable
fun Login(navController: NavHostController, viewModel: AuthViewModel) {
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.SignInSuccess -> {
                navController.navigate(Routes.ROUTE_HOME) {
                    popUpTo(Routes.ROUTE_LOGIN) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                // TODO: Mostrar mensaje de error
            }
            else -> {}
        }
    }

    BasicLayout(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
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
                    .padding(start = 10.dp, end = 10.dp, bottom = 16.dp)
                    .offset(y = (-130).dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Inicio de sesión",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
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

                Input(
                    placeholder = "Correo electrónico",
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Contraseña",
                    value = password,
                    onValueChange = { password = it },
                    suffixIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    text = "Iniciar sesión",
                    onClick = { viewModel.signIn(email, password) }
                )

                Spacer(modifier = Modifier.size(16.dp))

                TextButton(onClick = { navController.navigate(Routes.ROUTE_FORGOT_PASSWORD) },
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}