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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.CartItem
import mx.cazv.todasbrillamos.model.models.CartResponse
import mx.cazv.todasbrillamos.view.components.footer.ButtonBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel

/**
 * Archivo para mostrar el carrito de compras
 * @author Mariana Balderrábano
 */

/**
 * Pantalla del carrito de compras que muestra los productos agregados por el usuario.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun Cart(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel
) {
    val cartState by cartViewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()
        if (token != null) {
            cartViewModel.loadCart(token)
        }
    }

    CustomLayout (
        navController = navController,
        topBar = {
            BasicTopBar(title = "Mi carrito", navController = navController)
        },
        bottomBar = {
            ButtonBottomBar(
                buttonText = "Comprar (Total: $${String.format("%.2f", cartState.totalPrice)})",
                onClick = {}
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(Color(0xFFFCFAF2))
        ) {
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
                                cartViewModel.updateProductQuantityInCart(token, cartItem.product.product_id, newQuantity)
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