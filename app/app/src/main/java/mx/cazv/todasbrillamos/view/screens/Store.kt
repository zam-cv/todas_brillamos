package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.Product
import mx.cazv.todasbrillamos.model.ProductProvider
import mx.cazv.todasbrillamos.view.layouts.MainLayout


@Composable
fun Store(navController: NavHostController) {
    MainLayout(navController = navController) {
        Column {
            Text(text = "Store")
            LazyVerticalGrid(
                // columns = GridCells.Adaptive(160.dp),
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                itemsIndexed(ProductProvider.productList) {index, product ->
                    ProductGridItem(product = product)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ProductGridItem(product: Product) {
    val formattedPrice = "%.2f".format(product.precio)
    val formattedDC = "%.0f".format(product.descuento)

    Column (
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            Image(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(5.dp)),
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
            if (product.descuento != null) {
                Text(
                    text = "$formattedDC% Descuento",
                    style = TextStyle(
                        fontSize = 13.sp,
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

        Text(text = product.producto,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.wrapContentSize())

        Text(text = product.tipo,
            modifier = Modifier.wrapContentSize())

        if (product.descuento != null) {
            val discountPrice = "%.2f".format(product.precio * (1 - product.descuento / 100))
            Row {
                Text(text = "$$formattedPrice",
                    style = TextStyle(textDecoration = TextDecoration.LineThrough),
                    modifier = Modifier.wrapContentSize()
                )
                Text(text = "$$discountPrice",
                    modifier = Modifier.padding(start = 20.dp).wrapContentSize())
            }
        } else {
            Text(text = "$$formattedPrice")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun StorePreview() {
    val navController = rememberNavController()
    Store(navController = navController)
}