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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.models.UserInfo
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.viewmodel.AuthState
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

/**
 * Pantalla de registro que permite al usuario crear una nueva cuenta.
 * @author Jennyfer Jasso
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación utilizado para gestionar el registro del usuario.
 */
@Composable
fun Register(navController: NavHostController, authViewModel: AuthViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var receiveInfo by remember { mutableStateOf(false) }
    var acceptPrivacy by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.RegisterSuccess -> {
                navController.navigate(Routes.ROUTE_LOGIN) {
                    popUpTo(Routes.ROUTE_REGISTER) { inclusive = true }
                }
            }
            is AuthState.Error -> {}
            else -> {}
        }
    }

    BasicLayout(navController = navController) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)
            ) {
                Box(
                    modifier = Modifier
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
                            Input(
                                placeholder = "Nombre",
                                value = name,
                                onValueChange = { name = it }
                            )
                        }

                        Spacer(modifier = Modifier.size(10.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Input(
                                placeholder = "Apellido",
                                value = lastName,
                                onValueChange = { lastName = it },
                            )
                        }
                    }


                    Spacer(modifier = Modifier.size(16.dp))

                    Input(
                        placeholder = "Correo electrónico",
                        value = email,
                        onValueChange = { email = it }
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Input(
                        placeholder = "Contraseña",
                        suffixIcon = {
                            Icon(
                                Icons.Outlined.RemoveRedEye,
                                contentDescription = "Mostrar/Ocultar contraseña"
                            )
                        },
                        value = password,
                        onValueChange = { password = it },
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    Input(
                        placeholder = "Confirmar contraseña",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
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
                            checked = receiveInfo,
                            onCheckedChange = { receiveInfo = it }
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
                            checked = acceptPrivacy,
                            onCheckedChange = { acceptPrivacy = it }
                        )
                        Text(
                            text = "Acepto el aviso de privacidad",
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        text = "Continuar",
                        onClick = {
                            if (password == confirmPassword && acceptPrivacy) {
                                val userInfo = UserInfo(
                                    first_name = name,
                                    last_name = lastName,
                                    email = email,
                                    password = password
                                )

                                coroutineScope.launch {
                                    authViewModel.register(userInfo)
                                }
                            }
                        }
                    )

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
}

