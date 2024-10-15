package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.CartItem
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.footer.ButtonBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.BuyViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Archivo para mostrar el carrito de compras
 * @author Mariana Balderrábano, Carlos Zamudio
 */

/**
 * Pantalla del carrito de compras que muestra los productos agregados por el usuario.
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación.
 * @param cartViewModel El ViewModel del carrito de compras.
 */
@Composable
fun Cart(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    userViewModel: UserViewModel,
    buyViewModel: BuyViewModel
) {
    val cartState by cartViewModel.state.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var paymentIntentID by remember { mutableStateOf<String?>(null) }
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    val paymentSheet = rememberPaymentSheet { paymentResult ->
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                coroutineScope.launch {
                    val token = authViewModel.token() ?: ""
                    paymentIntentID?.let { id ->
                        val confirmResult = buyViewModel.confirmPayment(token, id)
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

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()
        if (token != null) {
            cartViewModel.loadCart(token)
        }

        val publishableKey = mx.cazv.todasbrillamos.model.Config.STRIPE_PUBLISHABLE_KEY
        PaymentConfiguration.init(context, publishableKey)
    }

    CustomLayout(
        navController = navController,
        topBar = {
            BasicTopBar(title = "Mi carrito", navController = navController)
        },
        bottomBar = {
            ButtonBottomBar(
                buttonText = if (isProcessing) "Procesando..." else "Comprar (Total: $${String.format("%.2f", cartState.totalPrice)})",
                onClick = {
                    coroutineScope.launch {
                        val token = authViewModel.token()

                        if (token != null) {
                            val exist = userViewModel.exist(token)

                            if (exist != null && exist.exists) {
                                isProcessing = true
                                errorMessage = null

                                try {
                                    val createIntentResult = buyViewModel.createPaymentIntent(token)

                                    if (createIntentResult.isSuccess) {
                                        val resultData = createIntentResult.getOrNull()
                                        val paymentIntentId = resultData?.paymentIntentId
                                        val clientSecret = resultData?.clientSecret
                                        if (paymentIntentId != null && clientSecret != null) {
                                            paymentIntentID = paymentIntentId
                                            paymentIntentClientSecret = clientSecret
                                            paymentSheet.presentWithPaymentIntent(
                                                paymentIntentClientSecret!!,
                                                PaymentSheet.Configuration(
                                                    merchantDisplayName = "Todas Brillamos",
                                                    allowsDelayedPaymentMethods = false
                                                )
                                            )
                                        } else {
                                            errorMessage = "Error al obtener el clientSecret"
                                            isProcessing = false
                                        }
                                    } else {
                                        errorMessage = "Error al crear la intención de pago"
                                        isProcessing = false
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                    isProcessing = false
                                }
                            } else {
                                navController.navigate(Routes.ROUTE_SHIPPING_INFO)
                            }
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(Color(0xFFFCFAF2))
        ) {
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (cartState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (cartState.error != null) {
                Text(
                    text = cartState.error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                cartState.cart.forEach { cartItem ->
                    Prod(
                        folder = cartState.folder,
                        item = cartItem,
                        onQuantityChange = { newQuantity ->
                            authViewModel.token()?.let { token ->
                                if (cartItem.product.stock >= newQuantity) {
                                    cartViewModel.updateProductQuantityInCart(token, cartItem.product.product_id, newQuantity)
                                }
                            }
                        },
                        onDelete = {
                            authViewModel.token()?.let { token ->
                                cartViewModel.deleteProductFromCart(token, cartItem.product.product_id)
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * Composable que muestra un producto en el carrito de compras.
 *
 * @param folder La carpeta donde se encuentra la imagen del producto.
 * @param item El producto a mostrar.
 * @param onQuantityChange La función que se llama cuando se cambia la cantidad del producto.
 * @param onDelete La función que se llama para eliminar el producto del carrito.
 */
@Composable
fun Prod(
    folder: String,
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val baseUrl = ApiConfig.BASE_URL
        val hash = item.product.hash
        val type = item.product.type
        val url = "$baseUrl$folder/$hash.$type"

        AsyncImage(
            model = url,
            contentDescription = item.product.name,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(bottom = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)){
            Text(text = item.product.name)

            Spacer(modifier = Modifier.height(24.dp))

            Row (modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFfae7ec))
                .padding(8.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){

                IconButton(
                    onClick = { onQuantityChange(item.quantity - 1) },
                    enabled = item.quantity > 1,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "disminuye",
                        tint = if (item.quantity > 1) Color.Black else Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "${item.quantity}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = { onQuantityChange(item.quantity + 1) },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12))
                        .background(Color.White)
                        .padding(1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "aumenta",
                        tint = Color.Black
                    )
                }
            }
        }

        Column (modifier = Modifier.weight(1f)) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }

            Text(
                text = "$${item.product.price * item.quantity}",
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}