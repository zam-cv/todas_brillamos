package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.Product


@Composable
fun ProductGridItem(product: Product) {
    val formattedPrice = "%.2f".format(product.precio)
    val formattedDC = "%.0f".format(product.descuento)
    
    Card (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(corner = CornerSize(5.dp))
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxSize(),
                    painter = painterResource(id = product.imagen),
                    contentDescription = product.producto,
                    contentScale = ContentScale.Crop
                )
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = (-10).dp, y = (-10).dp)
                        .align(Alignment.BottomEnd),
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "favorito"
                )
            }

            Box (modifier = Modifier.height(150.dp)){
                Column (modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp)
                ){
                    if (product.descuento != null) {
                        Row (modifier = Modifier.weight(0.8f)){
                            Box (modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFF4D0CB))
                                .size(50.dp)
                            ) {
                                Text(
                                    text = "-$formattedDC% ",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center ),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }


                            Text(
                                text = "Promoción",
                                color = Color(0xFFD5507C),
                                fontSize = 13.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 10.dp)
                                    .weight(2.8f)
                            )
                        }

                    }


                    Spacer (modifier = Modifier.height(8.dp))

                    Text(
                        text = product.producto,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = product.tipo,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W300,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize()
                    )

                    if (product.descuento != null) {
                        val discountPrice = "%.2f".format(product.precio * (1 - product.descuento / 100))
                        Row {
                            Text(
                                text = "$$discountPrice",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Left
                                ),
                                modifier = Modifier
                                    .wrapContentSize()
                            )
                            Text(
                                text = "$$formattedPrice",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W300,
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .padding(start = 20.dp)
                                    .wrapContentSize()
                            )
                        }
                    } else {
                        Text(
                            text = "$$formattedPrice",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentSize()
                        )
                    }
                }
            }

        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun GridViewPreview() {
    ViewProducts("grid")
}
*/