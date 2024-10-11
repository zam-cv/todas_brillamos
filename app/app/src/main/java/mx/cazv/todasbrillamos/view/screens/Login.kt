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
import androidx.compose.ui.graphics.Color
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
import mx.cazv.todasbrillamos.view.components.LabeledInput
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.viewmodel.AuthState
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

/**
 * Pantalla de inicio de sesión que permite al usuario ingresar su correo electrónico y contraseña para iniciar sesión.
 * @author Jennyfer Jasso, Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param viewModel El ViewModel de autenticación utilizado para gestionar el inicio de sesión del usuario.
 */
@Composable
fun Login(navController: NavHostController, viewModel: AuthViewModel) {
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isEmptyFieldError by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.SignInSuccess -> {
                navController.navigate(Routes.ROUTE_HOME) {
                    popUpTo(Routes.ROUTE_LOGIN) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                errorMessage = "Contraseña o correo inválidos. Por favor, inténta de nuevo."
                isEmptyFieldError = false
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
                Image(
                    painter = painterResource(id = R.drawable.degradado3),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillHeight
                )

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
                            text = "Únete a nosotros",
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.size(12.dp))

                LabeledInput(
                    placeholder = "Correo electrónico",
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = null
                        isEmptyFieldError = false
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    placeholder = "Contraseña",
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                        isEmptyFieldError = false
                    },
                    isPassword = true,
                )

                Spacer(modifier = Modifier.size(16.dp))

                // Mostrar el mensaje de error si existe

                if (isEmptyFieldError) {
                    Text(
                        text = "Por favor, completa todos los campos",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Button(
                    text = "Iniciar sesión",
                    onClick = {
                        email = email.trim()
                        password = password.trim()

                        if (email.isBlank() || password.isBlank()) {
                            isEmptyFieldError = true
                            errorMessage = null
                        } else {
                            isEmptyFieldError = false
                            errorMessage = null
                            viewModel.signIn(email, password)
                        }
                    }
                )

/*                Spacer(modifier = Modifier.size(16.dp))

                TextButton(
                    onClick = { navController.navigate(Routes.ROUTE_FORGOT_PASSWORD) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontSize = 15.sp
                    )
                }*/
            }
        }
    }
}