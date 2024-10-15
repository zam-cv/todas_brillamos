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
import mx.cazv.todasbrillamos.view.components.LabeledInput
import mx.cazv.todasbrillamos.view.components.footer.ButtonBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Archivo para la vista de datos de envío.
 * @author Mariana Balderrábano, Carlos Zamudio
 */

/**
 * Pantalla de información de envío que permite al usuario ingresar sus datos personales y de dirección.
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación (gestionar el registro del usuario).
 * @param userViewModel El ViewModel de usuario (gestionar las operaciones del usuario).
 */
@Composable
fun ShippingInfo(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    var curp by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var interior by remember { mutableStateOf("") }
    var exterior by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var reference by remember { mutableStateOf("") }

    var errors by remember { mutableStateOf(mapOf<String, String>()) }

    fun validateFields(): Boolean {
        val newErrors = mutableMapOf<String, String>()

        if (curp.length != 18) newErrors["curp"] = "CURP debe tener 18 caracteres"
        if (street.isBlank()) newErrors["street"] = "Calle es requerida"
        if (interior.toIntOrNull() == null) newErrors["interior"] = "Número interior debe ser un número"
        if (exterior.toIntOrNull() == null) newErrors["exterior"] = "Número exterior debe ser un número"
        if (city.isBlank()) newErrors["city"] = "Ciudad es requerida"
        if (state.isBlank()) newErrors["state"] = "Estado es requerido"
        if (zip.length < 4) newErrors["zip"] = "Código postal debe tener al menos 4 caracteres"

        errors = newErrors
        return errors.isEmpty()
    }

    CustomLayout (
        navController = navController,
        topBar = {
            BasicTopBar(title = "Datos de envío", navController = navController)
        },
        bottomBar = {
            ButtonBottomBar(buttonText = "Pagar", onClick = {
                if (validateFields()) {
                    curp = curp.trim()
                    street = street.trim()
                    interior = interior.trim()
                    exterior = exterior.trim()
                    city = city.trim()
                    state = state.trim()
                    zip = zip.trim()
                    reference = reference.trim()

                    val others = Others(
                        CURP = curp,
                        Street = street,
                        Interior = interior.toIntOrNull() ?: 0,
                        Exterior = exterior.toIntOrNull() ?: 0,
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
                                val success = userViewModel.setOthers(token, others)

                                if (success) {
                                    navController.navigate(Routes.ROUTE_CART)
                                } else {
                                    errors = mapOf("others" to "No se pudieron guardar los datos")
                                }
                            } catch (e: Exception) {
                                // Handle error
                            }
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

                LabeledInput(
                    label = "CURP",
                    placeholder = "Ejemplo: BADD110313HCMLNS09",
                    value = curp,
                    onValueChange = { curp = it },
                    required = true
                )

                Text(
                    text = "* El CURP es requerido por la fundación",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = "Dirección",
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Calle",
                    placeholder = "Ejemplo: Av. Insurgentes",
                    value = street,
                    onValueChange = { street = it },
                    required = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Número exterior",
                    placeholder = "Ejemplo: 123",
                    value = exterior,
                    onValueChange = { exterior = it },
                    required = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Número interior",
                    placeholder = "Ejemplo: 4",
                    value = interior,
                    onValueChange = { interior = it },
                    required = false
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Ciudad",
                    placeholder = "Ejemplo: Ciudad de México",
                    value = city,
                    onValueChange = { city = it },
                    required = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Estado",
                    placeholder = "Ejemplo: CDMX",
                    value = state,
                    onValueChange = { state = it },
                    required = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Código postal",
                    placeholder = "Ejemplo: 03900",
                    value = zip,
                    onValueChange = { zip = it },
                    required = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                LabeledInput(
                    label = "Referencia",
                    placeholder = "Ejemplo: Casa blanca con reja negra",
                    value = reference,
                    onValueChange = { reference = it },
                    required = false
                )

                Spacer(modifier = Modifier.size(16.dp))

                errors.entries.firstOrNull()?.let { (_, message) ->
                    Text(
                        text = message,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}