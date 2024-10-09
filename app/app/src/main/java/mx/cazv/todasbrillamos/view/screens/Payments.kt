package mx.cazv.todasbrillamos.view.screens

import android.app.Activity
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
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.confirmPaymentIntent
import com.stripe.android.model.CardParams
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentIntent
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentResult
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.Config
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.CustomButton
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.BuyViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel

/**
 * Archivo para mostrar el formulario de pagos.
 * @author Mariana Balderrábano
 */

/**
 * Pantalla de pagos que permite al usuario ingresar la información de su tarjeta y realizar un pago.
 *
 * @param navController El NavHostController utilizado para la navegación.
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

                        if (cardNumber.isBlank() || expiryDate.isBlank() || cvc.isBlank() || cardholderName.isBlank()) {
                            errorMessage = "Por favor, complete todos los campos."
                            isProcessing = false
                            return@launch
                        }

                        val (expiryMonth, expiryYear) = expiryDate.split("/")

                        val paymentMethodParams = PaymentMethodCreateParams.createCard(
                            CardParams(
                                number = cardNumber,
                                expMonth = expiryMonth.toInt(),
                                expYear = expiryYear.toInt(),
                                cvc = cvc,
                                name = cardholderName
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
                                errorMessage = createIntentResult.exceptionOrNull()?.message ?: "Error al crear la intención de pago"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error: ${e.message}"
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


