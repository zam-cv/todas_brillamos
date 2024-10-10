package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import mx.cazv.todasbrillamos.ui.theme.ImageBackgroundColor
import mx.cazv.todasbrillamos.view.Routes

/**
 * Vista de lista de los productos
 * @author: Min Che Kim
 *
 * @param product el producto a mostrar
 */
@Composable
fun ProductColumnItem(
    product: ProductRaw,
    folder: String,
    navController: NavHostController
) {
    val formattedPrice = product.price
    val formattedDC = "%.0f".format(0.0) // TODO: Aplicar descuento
    val discountPrice = 0.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .clickable {
                navController.navigate(Routes.ROUTE_PRODUCT_DETAILS + "/${product.id}")
            }
    ) {
        Box (
            modifier = Modifier
                .weight(2f)
                .height(110.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ImageBackgroundColor)
        ) {
            val base_url = ApiConfig.BASE_URL
            val hash = product.hash
            val type = product.type
            val url = "$base_url$folder/$hash.$type"

            AsyncImage(
                model = url,
                contentDescription = "Product",
                modifier = Modifier
                    .fillMaxSize()
                    .width(50.dp),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .weight(5f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFF4D0CB))
                    ) {
                        Text(
                            text = "-$formattedDC% ",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(5.dp)
                        )
                    }


                    Text(
                        text = "Promoción",
                        color = Color(0xFFD5507C),
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 10.dp)
                    )
                }


                Text(
                    text = product.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .wrapContentSize(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.model,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "$$formattedPrice.00",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .wrapContentSize()
                    )
                    Text(
                        text = "$$discountPrice",
                        fontSize = 13.sp,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(start = 20.dp)
                            .wrapContentSize()
                    )
                }

            }
        }

    }
}