package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.model.models.ClientDetails
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

@Composable
fun EditProfile(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
) {
    val userState by userViewModel.state.collectAsState()

    var firstName by remember { mutableStateOf(userState.details.first_name) }
    var lastName by remember { mutableStateOf(userState.details.last_name) }
    var email by remember { mutableStateOf(userState.details.email) }

    CustomLayout(
        navController = navController,
        withStoreButton = true,
        topBar = {
            BasicTopBar(title = "Editar Perfil", navController = navController)
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
                .padding(10.dp)
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = "Person",
                modifier = Modifier
                    .size(140.dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Box (
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Input(
                        placeholder = "Nombre",
                        value = firstName,
                        onValueChange = { firstName = it }
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                Box (
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Input(
                        placeholder = "Apellido",
                        value = lastName,
                        onValueChange = { lastName = it }
                    )
                }
            }

            Spacer(modifier = Modifier.size(25.dp))

            Input(
                placeholder = "Correo Electrónico",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                text = "Guardar Cambios",
                onClick = {
                    val clientDetails = ClientDetails(
                        first_name = firstName,
                        last_name = lastName,
                        email = email
                    )

                    authViewModel.getToken()?.let { token ->
                        userViewModel.update(token, clientDetails)
                    }
                }
            )
        }
    }
}