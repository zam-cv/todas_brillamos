package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.model.models.PasswordUpdate
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Eye
import mx.cazv.todasbrillamos.view.components.Input
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
            Input(
                placeholder = "Contraseña Actual",
                value = currentPassword,
                onValueChange = { currentPassword = it },
                suffixIcon = { Eye() }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Input(
                placeholder = "Nueva Contraseña",
                value = newPassword,
                onValueChange = { newPassword = it },
                suffixIcon = { Eye() }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Input(
                placeholder = "Confirmar contraseña",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                suffixIcon = { Eye() }
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Button(
                text = "Confirmar",
                onClick = {
                    if (newPassword == confirmPassword) {
                        val passwordUpdate = PasswordUpdate(
                            old_password = currentPassword,
                            new_password = newPassword
                        )
                        authViewModel.getToken()?.let { token ->
                            userViewModel.updatePassword(token, passwordUpdate)
                        }
                    } else {
                        // TODO: Show error message
                    }
                }
            )
        }
    }
}