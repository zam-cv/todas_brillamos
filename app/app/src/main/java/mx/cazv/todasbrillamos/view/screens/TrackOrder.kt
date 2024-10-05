package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.components.header.CustomTopBar
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.view.layouts.MainLayout

@Composable
fun TrackOrder(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

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

                Text(
                    text = "Entrega: ## ago ####",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffd5507c),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,

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

                        Text(
                            text = "Enviar a Fernanda Herrera, Calle ##### No. # Colonia wfhuqwhfiUHIOQ",
                            fontSize = 14.sp,
                            //fontWeight = FontWeight.Bold
                            //.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
                            maxLines = 1
                        )
                    }

                    OrderStatus(status = "Pedido recibido")


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
                //.weight(1f)
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

                OrderProducts(lenProducts = 3)
            }

/*            MoreProducts(
                text = "Más productos",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 25.dp, bottom = 25.dp)
            )*/
        }
    }
}

@Composable
fun OrderProducts(lenProducts: Int) {
    for (i in 1..lenProducts) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.temp_img),
                contentDescription = "Producto",
                modifier = Modifier
                    .size(80.dp)
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Row {
                    Text(text = "Producto",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f))
                    Text(
                        text = "$000.00",
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

@Composable
fun OrderStatus(status: String) {
    val statusList = listOf("Entregado", "En camino", "Preparando pedido", "Pedido recibido")
    val currentStatus = statusList.indexOf(status)

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
                            if (index >= currentStatus) {
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
                        text = s,
                        fontSize = 16.sp,
                        color = if (index >= currentStatus) Color(0xffd5507c) else Color.LightGray
                    )

                    Text(
                        text = "Agosto xx, xxxx, hh:mm am",
                        modifier = Modifier.padding(top = 2.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun TOPreview() {
    val navController = rememberNavController()
    TrackOrder(navController)
}
