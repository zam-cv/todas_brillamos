package mx.cazv.todasbrillamos.view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.OrderSummary
import mx.cazv.todasbrillamos.view.Routes
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(inputDate: String): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a", Locale("es", "ES"))

    val date = ZonedDateTime.parse(inputDate, inputFormatter)
    val formattedDate = date.format(outputFormatter)

    return formattedDate.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("es", "ES")) else it.toString() }
}

/**
 * Función composable que muestra la información de un pedido.
 * @author Jennyfer Jasso
 * @param folder La carpeta donde se encuentran las imágenes de los productos.
 * @param order El resumen del pedido.
 * @param navController El NavHostController utilizado para la navegación.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoOrder(
    folder: String,
    order: OrderSummary,
    navController: NavHostController
) {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .horizontalScroll(rememberScrollState())
            ) {

                order.products.forEach { product ->
                    val baseUrl = ApiConfig.BASE_URL
                    val url = "$baseUrl$folder/${product.hash}.${product.type}"

                    AsyncImage(
                        model = url,
                        contentDescription = "Product",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(top = 22.dp, start = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "MX ${order.total_price}.00 \n${order.total_products} artículos",
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    lineHeight = 14.sp
                )
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
        ) {
            Text(text = formatDate(order.delivery_date),
                fontSize = 14.sp,
                color = Color(0xffd5507c),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(top = 4.dp))

            androidx.compose.material3.Button(
                onClick = {
                    navController.navigate(Routes.ROUTE_TRACK_ORDER + "/${order.delivery_date}")
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xffd5507c)),
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(text = "Rastreo")
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp)
    }
}