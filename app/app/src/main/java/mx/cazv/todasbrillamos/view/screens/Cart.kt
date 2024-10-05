package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.components.footer.ButtonBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

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
fun Cart(navController: NavHostController) {
    CustomLayout (
        navController = navController,
        topBar = {
            BasicTopBar(title = "Mi carrito", navController = navController)
        },
        bottomBar = {
            ButtonBottomBar(buttonText = "Comprar", onClick = {})
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(Color(0xFFFCFAF2))
        ) {

                Prod()
                Prod()
                Prod()
                Prod()
                Prod()
                Prod()
                Prod()
                Prod()
                Prod()
                Prod()

        }
    }
}

/**
 * Composable que muestra un producto en el carrito de compras.
 */
@Composable
fun Prod() {
    var num by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val image = painterResource(id = R.drawable.foto)
        Image(
            painter = image,
            contentDescription = "Producto",
            modifier = Modifier
                //.size(60.dp)
                //.fillMaxWidth()
                .weight(1f)
                .aspectRatio(1f)
                .padding(bottom = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)){

            Text(text = "PRODUCTO")

            Spacer(modifier = Modifier.height(24.dp))

            Row (modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFfae7ec))
                .padding(8.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){

                IconButton(onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(1.dp)){
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        "disminuye",
                        tint = Color.Black


                    )
                }



                Spacer(modifier = Modifier.width(10.dp))

                Text(text = "$num", fontSize = 20.sp, modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12))
                        .background(Color.White)
                        .padding(1.dp)) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        "aumenta",
                        tint = Color.Black)
                }

            }

        }
        Column (modifier = Modifier.weight(1f)) {

            val precio by remember {mutableStateOf(0.0)}

            IconButton(onClick = { /* Acción del botón */ }, modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar",
                    tint = Color.Red)
            }

            Text(text = "$$precio", modifier = Modifier.align(Alignment.End))

        }

    }
}