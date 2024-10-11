package mx.cazv.todasbrillamos.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.BuyViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel

data class PaymentValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

fun validatePayment(
    cardNumber: String,
    expiryDate: String,
    cvc: String,
    cardholderName: String
): PaymentValidationResult {
    if (cardNumber.isBlank()) {
        return PaymentValidationResult(false, "El número de tarjeta no puede estar vacío")
    }
    if (!cardNumber.replace(" ", "").matches(Regex("^\\d{16}$"))) {
        return PaymentValidationResult(false, "El número de tarjeta debe tener 16 dígitos")
    }
    if (expiryDate.isBlank()) {
        return PaymentValidationResult(false, "La fecha de expiración no puede estar vacía")
    }
    if (!expiryDate.matches(Regex("^(0[1-9]|1[0-2])/\\d{2}$"))) {
        return PaymentValidationResult(false, "La fecha de expiración debe tener el formato MM/AA")
    }
    if (cvc.isBlank()) {
        return PaymentValidationResult(false, "El CVC no puede estar vacío")
    }
    if (!cvc.matches(Regex("^\\d{3,4}$"))) {
        return PaymentValidationResult(false, "El CVC debe tener 3 o 4 dígitos")
    }
    if (cardholderName.isBlank()) {
        return PaymentValidationResult(false, "El nombre del titular de la tarjeta no puede estar vacío")
    }
    if (cardholderName.length < 3) {
        return PaymentValidationResult(false, "El nombre del titular de la tarjeta debe tener al menos 3 caracteres")
    }
    return PaymentValidationResult(true)
}

/**
 * Archivo para mostrar el formulario de pagos.
 * @author Mariana Balderrábano, Jennyfer Jasso
 */

/**
 * Pantalla de pagos que permite al usuario ingresar la información de su tarjeta y realizar un pago.
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel para manejar las operaciones de autenticación.
 * @param cartViewModel El ViewModel para manejar las operaciones del carrito de compras.
 * @param buyViewModel El ViewModel para manejar las operaciones de compra.
 */
@Composable
fun Payments(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    buyViewModel: BuyViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var cardholderName by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var saveCardInfo by remember { mutableStateOf(true) }

    // Initialize Stripe
    val context = LocalContext.current
    val stripe = remember { Stripe(context, PaymentConfiguration.getInstance(context).publishableKey) }

    MainLayout(navController = navController) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.size(20.dp))
            NormalText(text = "Información de la tarjeta", sizeT = 15, colT = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            Input(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                placeholder = "1234 1234 1234 1234",
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp,
                imageId = R.drawable.cards,
                imageSize = 150.dp,
                padding = 1.dp,
                height = 65
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Input(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        placeholder = "MM/AA",
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 10.dp,
                        height = 65
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Input(
                        value = cvc,
                        onValueChange = { cvc = it },
                        placeholder = "CVC",
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 10.dp,
                        bottomStart = 0.dp,
                        imageId = R.drawable.cvc,
                        imageSize = 40.dp,
                        padding = 1.dp,
                        height = 65
                    )
                }
            }

            Spacer(modifier = Modifier.size(20.dp))

            NormalText(text = "Nombre del titular de la tarjeta", sizeT = 15, colT = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            Input(
                value = cardholderName,
                onValueChange = { cardholderName = it },
                placeholder = "Nombre completo"
            )

            Spacer(modifier = Modifier.size(34.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = saveCardInfo,
                    onCheckedChange = { saveCardInfo = it }
                )
                Column {
                    Text(
                        text = "Guardar mi información mediante un proceso de compra seguro en un clic",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    NormalText(text = "Paga con mayor rapidez en Powdur y en todos los comercios que acepten Link", sizeT = 14, colT = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.size(15.dp))

            Button(
                text = if (isProcessing) "Procesando..." else "Pagar",
                onClick = {
                    coroutineScope.launch {
                        isProcessing = true
                        errorMessage = null

                        val trimmedCardNumber = cardNumber.replace(" ", "").trim()
                        val trimmedExpiryDate = expiryDate.trim()
                        val trimmedCvc = cvc.trim()
                        val trimmedCardholderName = cardholderName.trim()

                        val validationResult = validatePayment(
                            cardNumber = trimmedCardNumber,
                            expiryDate = trimmedExpiryDate,
                            cvc = trimmedCvc,
                            cardholderName = trimmedCardholderName
                        )

                        if (validationResult.isValid) {
                            val (expiryMonth, expiryYear) = expiryDate.split("/")

                            val paymentMethodParams = PaymentMethodCreateParams.createCard(
                                CardParams(
                                    number = trimmedCardNumber,
                                    expMonth = expiryMonth.toInt(),
                                    expYear = expiryYear.toInt(),
                                    cvc = trimmedCvc,
                                    name = trimmedCardholderName
                                )
                            )

                            try {
                                val token = authViewModel.token() ?: ""
                                val createIntentResult = buyViewModel.createPaymentIntent(token)

                                if (createIntentResult.isSuccess) {
                                    val clientSecret = createIntentResult.getOrNull()
                                    if (clientSecret != null) {
                                        val confirmParams = ConfirmPaymentIntentParams
                                            .createWithPaymentMethodCreateParams(paymentMethodParams, clientSecret)

                                        stripe.confirmPayment(ComponentActivity(), confirmParams)
                                        cartViewModel.buy()
                                        navController.navigate(Routes.ROUTE_HOME)
                                    } else {
                                        errorMessage = "Error al obtener el clientSecret"
                                    }
                                } else {
                                    errorMessage = "Error al crear la intención de pago"
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.message}"
                            }
                        } else {
                            errorMessage = validationResult.errorMessage
                        }

                        isProcessing = false
                    }
                }
            )

            Spacer(modifier = Modifier.size(40.dp))
        }
    }
}

/**
 * Composable que muestra un texto con un estilo específico.
 *
 * @param text El texto a mostrar.
 * @param sizeT El tamaño del texto.
 * @param colT El color del texto.
 */
@Composable
fun NormalText(text: String, sizeT: Int, colT: Color){
    Text(text = text,
        modifier = Modifier,
        fontSize = sizeT.sp,
        color = colT,
        fontWeight = FontWeight.W400)
}
