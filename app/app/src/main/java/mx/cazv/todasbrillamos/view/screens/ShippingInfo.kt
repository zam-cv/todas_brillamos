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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
                            navController.navigate(Routes.ROUTE_PAYMENTS)
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
                    placeholder = "Ejemplo: 4B (opcional)",
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
            }
        }
    }
}

@Composable
fun LabeledInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    required: Boolean
) {
    Column {
        Text(
            buildAnnotatedString {
                append(label)
                if (required) {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append(" *")
                    }
                }
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Input(
            placeholder = placeholder,
            value = value,
            onValueChange = onValueChange
        )
    }
}