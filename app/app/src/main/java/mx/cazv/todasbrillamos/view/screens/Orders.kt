package mx.cazv.todasbrillamos.view.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.InfoOrder
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.TrackingViewModel

/**
 * Pantalla de pedidos que muestra una lista de pedidos realizados por el usuario.
 * @author Jennyfer Jasso
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El AuthViewModel utilizado para obtener el token de autenticación.
 * @param trackingViewModel El TrackingViewModel utilizado para obtener la información de los pedidos.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Orders(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    trackingViewModel: TrackingViewModel
) {
    val trackingState = trackingViewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            trackingViewModel.loadTracking(token)
        }
    }

    CustomLayout(
        withStoreButton = true,
        withScroll = trackingState.value.orders.isNotEmpty(),
        navController = navController,
        topBar = {
            BasicTopBar(title = "Pedidos", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        if (trackingState.value.orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay pedidos que mostrar")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp)
            ){
                trackingState.value.orders.forEach { order ->
                    InfoOrder(
                        folder = trackingState.value.folder,
                        order = order,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}