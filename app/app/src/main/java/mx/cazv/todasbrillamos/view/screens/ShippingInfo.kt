package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.model.models.Others
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.components.footer.ButtonBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Archivo para la vista de datos de envío.
 * @author Mariana Balderrábano
 */

/**
 * Pantalla de información de envío que permite al usuario ingresar sus datos personales y de dirección.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun ShippingInfo(
    navController: NavHostController,
    productId: Int,
    quantity: Int,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    cartViewModel: CartViewModel,
) {
    var curp by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var interior by remember { mutableStateOf("") }
    var exterior by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var reference by remember { mutableStateOf("") }

    CustomLayout (
        navController = navController,
        topBar = {
            BasicTopBar(title = "Datos de envío", navController = navController)
        },
        bottomBar = {
            ButtonBottomBar(buttonText = "Pagar", onClick = {
                val others = Others(
                    CURP = curp,
                    Street = street,
                    Interior = interior,
                    Exterior = exterior,
                    City = city,
                    State = state,
                    ZIP = zip,
                    Reference = reference
                )

                CoroutineScope(Dispatchers.Main).launch {
                    val token = withContext(Dispatchers.IO) {
                        authViewModel.token()
                    }

                    if (token != null) {
                        try {
                            userViewModel.setOthers(token, others)
                            cartViewModel.addProductToCart(token, productId, quantity)
                            navController.navigate(Routes.ROUTE_CART)
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(Color(0xFFFCFAF2))
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "CURP",
                    value = curp,
                    onValueChange = { curp = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = "Dirección",
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Calle",
                    value = street,
                    onValueChange = { street = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Número exterior",
                    value = exterior,
                    onValueChange = { exterior = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Número interior",
                    value = interior,
                    onValueChange = { interior = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Ciudad",
                    value = city,
                    onValueChange = { city = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Estado",
                    value = state,
                    onValueChange = { state = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Código postal",
                    value = zip,
                    onValueChange = { zip = it }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Input(
                    placeholder = "Referencia",
                    value = reference,
                    onValueChange = { reference = it }
                )

                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}