package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.BuyViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet

/**
 * Pantalla de pagos que permite al usuario ingresar la información de su tarjeta y realizar un pago.
 */
@Composable
fun Payments(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    buyViewModel: BuyViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }
    val paymentSheet = rememberPaymentSheet { paymentResult ->
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                coroutineScope.launch {
                    val token = authViewModel.token() ?: ""
                    paymentIntentClientSecret?.let { secret ->
                        val confirmResult = buyViewModel.confirmPayment(token, secret)
                        if (confirmResult.isSuccess) {
                            cartViewModel.buy()
                            navController.navigate(Routes.ROUTE_HOME)
                        } else {
                            errorMessage = "Error al confirmar el pago en el servidor"
                        }
                    } ?: run {
                        errorMessage = "Error: clientSecret es nulo"
                    }
                }
            }
            is PaymentSheetResult.Canceled -> {
                errorMessage = "Pago cancelado"
            }
            is PaymentSheetResult.Failed -> {
                errorMessage = "Error en el pago: ${paymentResult.error.message}"
            }
        }
        isProcessing = false
    }

    LaunchedEffect(Unit) {
        val publishableKey = mx.cazv.todasbrillamos.model.Config.STRIPE_PUBLISHABLE_KEY
        PaymentConfiguration.init(context, publishableKey)
    }

    MainLayout(navController = navController) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                text = if (isProcessing) "Procesando..." else "Pagar",
                onClick = {
                    coroutineScope.launch {
                        isProcessing = true
                        errorMessage = null

                        try {
                            val token = authViewModel.token() ?: ""
                            val createIntentResult = buyViewModel.createPaymentIntent(token)
                            val value = createIntentResult.getOrNull()

                            if (createIntentResult.isSuccess && value != null) {
     /*                           paymentIntentClientSecret = createIntentResult.getOrNull().paymentIntentId
                                if (paymentIntentClientSecret != null) {

                                } else {
                                    errorMessage = "Error al obtener el clientSecret"
                                    isProcessing = false
                                }*/

                                paymentIntentClientSecret = value.paymentIntentId
                                paymentSheet.presentWithPaymentIntent(
                                    paymentIntentClientSecret!!,
                                    PaymentSheet.Configuration(
                                        merchantDisplayName = "Tu Nombre de Comercio",
                                        allowsDelayedPaymentMethods = false
                                    )
                                )
                            } else {
                                errorMessage = "Error al crear la intención de pago"
                                isProcessing = false
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error: ${e.message}"
                            isProcessing = false
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.size(40.dp))
        }
    }
}
