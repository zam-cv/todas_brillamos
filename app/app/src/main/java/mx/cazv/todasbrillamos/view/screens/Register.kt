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
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.models.UserInfo
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.LabeledInput
import mx.cazv.todasbrillamos.viewmodel.AuthState
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

fun validateRegistration(
    email: String,
    password: String,
    confirmPassword: String,
    firstName: String,
    lastName: String,
    acceptPrivacy: Boolean
): ValidationResult {
    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return ValidationResult(false, "Correo electrónico inválido")
    }
    if (password.length < 8) {
        return ValidationResult(false, "La contraseña debe tener al menos 8 caracteres")
    }
    if (password != confirmPassword) {
        return ValidationResult(false, "Las contraseñas no coinciden")
    }
    if (firstName.length < 2) {
        return ValidationResult(false, "El nombre debe tener al menos 2 caracteres")
    }
    if (lastName.length < 2) {
        return ValidationResult(false, "El apellido debe tener al menos 2 caracteres")
    }
    if (!acceptPrivacy) {
        return ValidationResult(false, "Debes aceptar el aviso de privacidad")
    }
    return ValidationResult(true)
}

/**
 * Pantalla de registro que permite al usuario crear una nueva cuenta.
 * @author Jennyfer Jasso, Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación utilizado para gestionar el registro
 * del usuario.
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
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.RegisterSuccess -> {
                navController.navigate(Routes.ROUTE_LOGIN) {
                    popUpTo(Routes.ROUTE_REGISTER) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                errorMessage = (authState as AuthState.Error).message
            }
            else -> {}
        }
    }

    BasicLayout(navController = navController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.degradado3),
                        contentDescription = "Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logo_tb),
                        contentDescription = "Logo",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Column (
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Regístrate",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LabeledInput(
                        label = "Nombre",
                        placeholder = "Ejemplo: María",
                        value = name,
                        onValueChange = { name = it },
                        required = true,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Apellido",
                        placeholder = "Ejemplo: González",
                        value = lastName,
                        onValueChange = { lastName = it },
                        required = true,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Correo electrónico",
                        placeholder = "Ejemplo: m@ejemplo.com",
                        value = email,
                        onValueChange = { email = it },
                        required = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Contraseña",
                        placeholder = "",
                        value = password,
                        onValueChange = { password = it },
                        required = true,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Confirmar contraseña",
                        placeholder = "Repite tu contraseña",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        required = true,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

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
                            fontSize = 12.sp,
                            style = TextStyle(lineHeight = 15.sp)
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
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        "Continuar",
                        onClick = {
                            val validationResult = validateRegistration(
                                email = email,
                                password = password,
                                confirmPassword = confirmPassword,
                                firstName = name,
                                lastName = lastName,
                                acceptPrivacy = acceptPrivacy
                            )

                            if (validationResult.isValid) {
                                val userInfo = UserInfo(
                                    first_name = name,
                                    last_name = lastName,
                                    email = email,
                                    password = password
                                )

                                coroutineScope.launch {
                                    authViewModel.register(userInfo)
                                }
                            } else {
                                errorMessage = validationResult.errorMessage
                            }
                        }
                    )

                    errorMessage?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = { navController.navigate(Routes.ROUTE_LOGIN) }) {
                        Text(
                            text = "Ya tengo una cuenta",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
