package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.PasswordUpdate
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.AlertDialogExample
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Eye
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.components.LabeledInput
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Pantalla de cambio de contraseña que permite al usuario actualizar su contraseña.
 * @author Carlos Zamudio
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun ChangePassword(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var hasError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var currentPasswordError by remember { mutableStateOf<String?>(null) }
    var newPasswordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    var isButtonEnabled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()


    CustomLayout(
        navController = navController,
        withStoreButton = true,
        topBar = {
            BasicTopBar(title = "Cambiar contraseña", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(start = 10.dp, end = 10.dp, top = 30.dp)
        ) {
            LabeledInput(
                placeholder = "Contraseña Actual",
                value = currentPassword,
                onValueChange = { currentPassword = it },
                isPassword = true,
                errorMessage = currentPasswordError
            )

            Spacer(modifier = Modifier.padding(15.dp))

            LabeledInput(
                placeholder = "Nueva Contraseña",
                value = newPassword,
                onValueChange = { newPassword = it },
                isPassword = true,
                errorMessage = newPasswordError
            )

            Spacer(modifier = Modifier.padding(15.dp))

            LabeledInput(
                placeholder = "Confirmar contraseña",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPassword = true,
                errorMessage = confirmPasswordError
            )

            Spacer(modifier = Modifier.padding(15.dp))

            if (currentPassword.isEmpty() && newPassword.isEmpty() && confirmPassword.isEmpty()){
                isButtonEnabled = false
                hasError = false
                currentPasswordError = null
                newPasswordError = null
                confirmPasswordError = null
            } else {
                isButtonEnabled = true
            }

            Button(
                text = "Confirmar",
                enabled = isButtonEnabled,
                onClick = {

                    currentPasswordError = if (currentPassword.isEmpty()){
                        "La contraseña actual no puede estar vacía"
                    } else {
                        null
                    }

                    newPasswordError = if (newPassword.isEmpty()){
                        "La nueva contraseña no puede estar vacía"
                    } else if (newPassword.length < 8) {
                        "La nueva contraseña debe tener al menos 8 caracteres"
                    } else {
                        null
                    }

                    confirmPasswordError = if (confirmPassword != newPassword){
                        "Las contraseñas no coinciden"
                    } else {
                        null
                    }

                    hasError = currentPasswordError != null || newPasswordError != null || confirmPasswordError != null

                    if (!hasError){

                        val passwordUpdate = PasswordUpdate(
                            old_password = currentPassword,
                            new_password = newPassword
                        )
//                        authViewModel.token()?.let { token ->
//                            userViewModel.updatePassword(token, passwordUpdate)
//                        }

                        coroutineScope.launch {
                            val token = authViewModel.token()
                            if (token != null) {
                                val success = userViewModel.updatePassword(token, passwordUpdate)

                                if (success) {
                                    showDialog = true
                                } else {
                                    hasError = true
                                    currentPasswordError = "La contraseña actual es incorrecta"
                                }
                            }
                        }
                    }
                    else {

                    }
                }
            )


        }
    }

    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = { },
            onConfirmation = { showDialog = false
                 isButtonEnabled = false
                 currentPassword = ""
                 newPassword = ""
                 confirmPassword = ""},
            dialogTitle = "Constraseña actualizada",
            dialogText = "Tu contraseña ha sido actualizada.",
            icon = Icons.Outlined.Lock
        )
    }


}