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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.Product


@Composable
fun ProductColumnItem(product: Product) {
    val formattedPrice = "%.2f".format(product.precio)
    val formattedDC = "%.0f".format(product.descuento)
    val fontSize = 20.sp

    Row (
        modifier = Modifier
            .height(130.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.width(130.dp)){
            Image(
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(5.dp)),
                painter = painterResource(id = product.imagen),
                contentDescription = product.producto,
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = (-5).dp, y = (-5).dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = "favorito"
            )
            if (product.descuento != null) {
                Text(
                    text = "$formattedDC% ",
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFF4D0CB))
                        .padding(7.dp)
                )
            }
        }


        Spacer (modifier = Modifier.height(8.dp))

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(13.dp)){
            Text(text = product.producto,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.wrapContentSize())

            Text(text = product.tipo,
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .wrapContentSize())

            if (product.descuento != null) {
                val discountPrice = "%.2f".format(product.precio * (1 - product.descuento / 100))
                Row {
                    Text(text = "$$discountPrice",
                        fontSize = fontSize,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Text(text = "$$formattedPrice",
                        fontSize = 15.sp,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(start = 20.dp)
                            .wrapContentSize()
                    )
                }
            } else {
                Text(text = "$$formattedPrice",
                    fontSize = fontSize,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .wrapContentSize())
            }
        }

    }
}