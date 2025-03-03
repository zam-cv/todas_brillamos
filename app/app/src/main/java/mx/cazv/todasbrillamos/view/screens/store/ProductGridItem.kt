package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.ProductRaw
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.view.Routes

/**
 * Vista cuadricula de los productos que muestra los detalles de un producto en una tarjeta.
 * @author: Min Che Kim, Carlos Zamudio
 *
 * @param product objeto [ProductRaw] que es el producto a mostrar.
 * @param folder la carpeta de la imagen del producto.
 * @param navController el controlador de navegación.
 */
@Composable
fun ProductGridItem(
    product: ProductRaw,
    folder: String,
    navController: NavHostController
) {
    val formattedPrice = product.price
    val formattedDC = "%.0f".format(0.0) // TODO: Aplicar descuento
    val discountPrice = 0.0
    
    Card (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                navController.navigate(Routes.ROUTE_PRODUCT_DETAILS + "/${product.id}")
            },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                val base_url = ApiConfig.BASE_URL
                val hash = product.hash
                val type = product.type
                val url = "$base_url$folder/$hash.$type"
                println(url)

                AsyncImage(
                    model = url,
                    contentDescription = "Product",
                    modifier = Modifier
                        .fillMaxSize()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Box {
                Column (
                    modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp)
                ){
/*                    Row {
                        Box (modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFF4D0CB))
                        ) {
                            Text(
                                text = "-$formattedDC% ",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(5.dp)
                            )
                        }


                        Text(
                            text = "Promoción",
                            color = Color(0xFFD5507C),
                            fontSize = 13.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 10.dp)
                        )
                    }*/

                    Spacer (modifier = Modifier.height(8.dp))

                    Text(
                        text = product.name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .wrapContentSize(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = product.model,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W300,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                    )

                    Spacer (modifier = Modifier.height(8.dp))

                    Row {
                        Text(
                            text = "$formattedPrice.00",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                        )
/*                        Text(
                            text = "$$discountPrice",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W300,
                                textDecoration = TextDecoration.LineThrough
                            ),
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .padding(start = 20.dp)
                                .wrapContentSize()
                        )*/
                    }
                }
            }
        }
    }
}