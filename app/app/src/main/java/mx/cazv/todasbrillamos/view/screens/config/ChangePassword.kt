package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.CustomTopBar
import mx.cazv.todasbrillamos.view.components.Eye
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun ChangePassword(navController: NavHostController) {
    CustomLayout (
        navController = navController,
        topBar = {
            CustomTopBar {
                Text(text = "Custom Top Bar")
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(start = 10.dp, end = 10.dp, top = 30.dp)
        ) {
            Input("Contraseña Actual", suffixIcon = {
                Eye()
            })

            Spacer(modifier = Modifier.padding(15.dp))

            Input("Nueva Contraseña", suffixIcon = {
                Eye()
            })

            Spacer(modifier = Modifier.padding(15.dp))

            Input("Confirmar contraseña", suffixIcon = {
                Eye()
            })

            Spacer(modifier = Modifier.padding(15.dp))

            Button("Confirmar")
        }
    }
}