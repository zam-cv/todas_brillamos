package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.TrackingOrder
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.formatDate
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.RandomViewModel
import mx.cazv.todasbrillamos.viewmodel.TrackingViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Archivo que contiene la vista de rastreo de pedido.
 * @author Carlos Zamudio
 * @author Jennyfer Jasso
 *
 */

/**
 * Pantalla de rastreo de pedido que muestra el estado del pedido y los detalles del mismo.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun TrackOrder(
    navController: NavHostController,
    deliveryDate: String,
    authViewModel: AuthViewModel,
    trackingViewModel: TrackingViewModel,
    userViewModel: UserViewModel,
    randomViewModel: RandomViewModel
) {
    var address by remember { mutableStateOf("") }
    val trackingOrderState = trackingViewModel.stateOrder.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            val date = deliveryDate.split("T")[0]
            trackingViewModel.loadTrackingByDate(token, date)
            address = userViewModel.getAddress(token)
        }
    }

    CustomLayout(
        withStoreButton = true,
        navController = navController,
        topBar = {
            BasicTopBar(title = "Rastreo pedido", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(Color.White)
            ) {
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 15.dp)
                )

                val deliveryDateFormatted = formatDate(deliveryDate)

                Text(
                    text = "Entrega: $deliveryDateFormatted",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffd5507c),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 15.dp)
                )

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                    ) {
                        val fullname = userViewModel.state.value.details.first_name + " " + userViewModel.state.value.details.last_name

                        Text(
                            text = "Enviar a $fullname, $address",
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }

                    OrderStatus(
                        status = "Pedido recibido",
                        trackingOrder = trackingOrderState.value
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .background(Color.White)
            ) {
                Text(text = "Detalles del pedido",
                    color = Color(0xffd5507c),
                    modifier = Modifier
                        .padding(start = 15.dp, top = 18.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp))

                OrderProducts(trackingOrderState.value)
            }

            MoreProducts(
                text = "Más productos",
                products = randomViewModel.state.value.products,
                navController = navController,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 45.dp, bottom = 25.dp)
            )
        }
    }
}

/**
 * Composable que muestra los productos del pedido.
 *
 * @param lenProducts La cantidad de productos en el pedido.
 */
@Composable
fun OrderProducts(trackingOrder: TrackingOrder) {
    trackingOrder.order.products.forEach { product ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val baseUrl = ApiConfig.BASE_URL
            val url = "$baseUrl${trackingOrder.folder}/${product.hash}.${product.type}"

            AsyncImage(
                model = url,
                contentDescription = "Product",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Row {
                    Text(text = product.product_name,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f))

                    val price = product.price.toString()

                    Text(
                        text = "$$price.00",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "x2",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth())
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
        )
    }
}

/**
 * Composable que muestra el estado del pedido.
 *
 * @param status El estado actual del pedido.
 * @param trackingOrder El objeto de rastreo del pedido.
 */
@Composable
fun OrderStatus(
    status: String,
    trackingOrder: TrackingOrder
) {
    val statusList = listOf(
        Pair("Pedido recibido", trackingOrder.order.order_received_date),
        Pair("Preparando pedido", trackingOrder.order.preparing_order_date),
        Pair("En camino", trackingOrder.order.shipped_date),
        Pair("Entregado", trackingOrder.order.delivery_date)
    )

    val currentStatus = statusList.indexOfFirst { it.first == status }

    Column {
        statusList.forEachIndexed { index, s ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentStatus) {
                                Color(0xffd5507c)
                            } else {
                                Color.LightGray
                            }
                        ),
                ){}

                Spacer(modifier = Modifier.width(4.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp)
                ) {
                    Text(
                        text = s.first,
                        fontSize = 16.sp,
                        color = if (index == currentStatus) Color(0xffd5507c) else Color.Black
                    )

                    if (s.second != null && s.second!!.isNotEmpty()) {
                        Text(
                            text = formatDate(s.second!!),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    } else {
                        Text(
                            text = "Pendiente",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
