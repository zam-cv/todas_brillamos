package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.model.models.Product
import mx.cazv.todasbrillamos.ui.theme.AccentColor

/**
 * Vista de lista de los productos
 * @author: Min Che Kim
 *
 * @param product el producto a mostrar
 */
@Composable
fun ProductColumnItem(product: Product) {
/*    val formattedPrice = "%.2f".format(product.product.price)
    val formattedDC = "0.0"
    val productFontSize = 15.sp
    val priceFontSize = 18.sp
    val priceLineSize = 13.sp
    val dcFontSize = 15.sp
    val size = 120.dp

    Row (
        modifier = Modifier
            .height(size)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Box(modifier = Modifier.width(size)){
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(5.dp)),
                painter = painterResource(id = product.imagen),
                contentDescription = product.product.name,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-5).dp, y = (-5).dp)
            ) {
                // Background with independent offset
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.BottomEnd)
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                    // This only moves the background
                )

                Icon (
                    imageVector = (if (product.favorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder),
                    contentDescription = "favorito",
                    tint = (if (product.favorito) AccentColor else Color.Black),
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.Center)
                        .offset(y = 2.dp)
                )

            }
        }

        Box (modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        ){
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                if (product.descuento != null) {
                    Row(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFF4D0CB))
                                .size(50.dp)
                        ) {
                            Text(
                                text = "-$formattedDC% ",
                                style = TextStyle(
                                    fontSize = dcFontSize,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }


                        Text(
                            text = "Promoción",
                            color = Color(0xFFD5507C),
                            fontSize = dcFontSize,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 10.dp)
                                .weight(3.5f)
                        )
                    }
                }

                Text(
                    text = product.producto,
                    fontSize = productFontSize,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.tipo,
                    fontSize = productFontSize,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize()
                )

                if (product.descuento != null) {
                    val discountPrice =
                        "%.2f".format(product.precio * (1 - product.descuento / 100))
                    Row(modifier = Modifier
                        .weight(1f)
                        .wrapContentSize()) {
                        Text(
                            text = "$$discountPrice",
                            fontSize = priceFontSize,
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .wrapContentSize()
                        )
                        Text(
                            text = "$$formattedPrice",
                            fontSize = priceLineSize,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .padding(start = 20.dp)
                                .wrapContentSize()
                        )
                    }
                } else {
                    Text(
                        text = "$$formattedPrice",
                        fontSize = priceFontSize,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize()
                    )
                }
            }
        }

    }*/
}

/*
@Preview(showBackground = true)
@Composable
fun ListViewPreview() {
    ViewProducts("list")
}
*/