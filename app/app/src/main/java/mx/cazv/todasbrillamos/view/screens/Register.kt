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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.models.UserInfo
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.AlertDialogExample
import mx.cazv.todasbrillamos.view.components.LabeledInput
import mx.cazv.todasbrillamos.viewmodel.AuthState
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

data class ValidationResult(
    val isValid: Boolean,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val privacyError: String? = null
)

fun validateField(value: String, rules: List<(String) -> String?>): String? {
    for (rule in rules) {
        val error = rule(value)
        if (error != null) return error
    }
    return null
}

fun validateRegistration(
    email: String,
    password: String,
    confirmPassword: String,
    firstName: String,
    lastName: String,
    acceptPrivacy: Boolean
): ValidationResult {

    var emailError: String? = null
    var passwordError: String? = null
    var confirmPasswordError: String? = null
    var firstNameError: String? = null
    var lastNameError: String? = null
    var privacyError: String? = null

    val nameRegex = "^[a-zA-Z\\s]+$".toRegex()

    val errors = mutableMapOf<String, String?>()



    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        emailError = "Correo electrónico inválido"
    }
    if (password.length < 8) {
        passwordError = "La contraseña debe tener al menos 8 caracteres"
    }
    if (password != confirmPassword) {
        confirmPasswordError = "Las contraseñas no coinciden"
    }
    if (firstName.length < 2 || !firstName.matches(nameRegex)) {
        firstNameError = "El nombre solo debe contener letras y al menos 2 caracteres"
    }
    if (lastName.length < 2 || !lastName.matches(nameRegex)) {
        lastNameError = "El apellido solo debe contener letras y al menos 2 caracteres"
    }
    if (!acceptPrivacy) {
        privacyError = "Debes aceptar el aviso de privacidad"
    }


    val isValid = emailError == null && passwordError == null && confirmPasswordError == null &&
            firstNameError == null && lastNameError == null && privacyError == null

    return ValidationResult(
        isValid = isValid,
        emailError = emailError,
        passwordError = passwordError,
        confirmPasswordError = confirmPasswordError,
        firstNameError = firstNameError,
        lastNameError = lastNameError,
        privacyError = privacyError
    )
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
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var receiveInfo by rememberSaveable { mutableStateOf(false) }
    var acceptPrivacy by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

//    var showError by rememberSaveable { mutableStateOf(false) }
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var lastNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmPasswordError by rememberSaveable { mutableStateOf<String?>(null) }
    var acceptPrivacyError by rememberSaveable { mutableStateOf<String?>(null) }

    var showDialog by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()

    val nameRegex = "^[a-zA-Z\\s]+$".toRegex()


    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.RegisterSuccess -> {
                showDialog = true
//                navController.navigate(Routes.ROUTE_LOGIN) {
//                    popUpTo(Routes.ROUTE_REGISTER) { inclusive = true }
//                }
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

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "¡Únete a nosotros!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LabeledInput(
                        label = "Nombre",
                        placeholder = "Ejemplo: María",
                        value = name,
                        onValueChange = {
                            name = it
                            nameError =
                                if (it.length > 2 && it.matches(nameRegex)) null else "El nombre solo debe contener letras y al menos 2 caracteres"

                        },
                        required = true,
                        errorMessage = nameError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Apellido",
                        placeholder = "Ejemplo: González",
                        value = lastName,
                        onValueChange = {
                            lastName = it
                            lastNameError =
                                if (it.length > 2 && it.matches(nameRegex)) null else "El apellido solo debe contener letras y al menos 2 caracteres"
                        },
                        required = true,
                        errorMessage = lastNameError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Correo electrónico",
                        placeholder = "Ejemplo: m@ejemplo.com",
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (android.util.Patterns.EMAIL_ADDRESS.matcher(it)
                                    .matches()
                            ) null else "Correo electrónico inválido"
                        },
                        required = true,
                        errorMessage = emailError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Contraseña",
                        placeholder = "Debe tener al menos 8 caracteres",
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError =
                                if (it.length < 8) "La contraseña debe tener al menos 8 caracteres" else null
                        },
                        required = true,
                        isPassword = true,
                        errorMessage = passwordError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LabeledInput(
                        label = "Confirmar contraseña",
                        placeholder = "Repite tu contraseña",
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError =
                                if (it != password) "Las contraseñas no coinciden" else null
                        },
                        required = true,
                        isPassword = true,
                        errorMessage = confirmPasswordError
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
                            onCheckedChange = {
                                acceptPrivacy = it
                                acceptPrivacyError =
                                    if (!it) "Debes aceptar el aviso de privacidad" else null
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontStyle = FontStyle.Normal)) {
                                    append("Acepto el ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                ) {
                                    append("Aviso de Privacidad")
                                }
                            },
                            fontSize = 12.sp,
                            modifier = Modifier.clickable { navController.navigate(Routes.ROUTE_TERMS_AND_POLICIES_REGISTER) }
                        )
                    }

                    if (acceptPrivacyError != null) {
                        Text(
                            text = acceptPrivacyError!!,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        "Continuar",
                        onClick = {
//                            showError = true

                            email = email.trim()
                            password = password.trim()
                            confirmPassword = confirmPassword.trim()
                            name = name.trim()
                            lastName = lastName.trim()

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
                                nameError = validationResult.firstNameError
                                lastNameError = validationResult.lastNameError
                                emailError = validationResult.emailError
                                passwordError = validationResult.passwordError
                                confirmPasswordError = validationResult.confirmPasswordError
                                acceptPrivacyError = validationResult.privacyError
                            }
                        }
                    )


//                    errorMessage?.let { error ->
//                        Text(
//                            text = error,
//                            color = Color.Red,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .padding(top = 8.dp)
//                                .fillMaxWidth()
//                                .wrapContentWidth(Alignment.CenterHorizontally)
//                        )
//                    }

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

    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                showDialog = false
                authViewModel.resetAuthState()

                navController.navigate(Routes.ROUTE_LOGIN) {
                    popUpTo(Routes.ROUTE_REGISTER) { inclusive = true }
                }
            },
            dialogTitle = "Registro exitoso",
            dialogText = "Ahora puedes iniciar sesión con tu cuenta",
            icon = Icons.Outlined.Check
        )
    }
}
