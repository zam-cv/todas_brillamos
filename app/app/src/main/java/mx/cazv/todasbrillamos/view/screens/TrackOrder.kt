package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.CustomTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun TrackOrder(navController: NavHostController) {
    CustomLayout(
        navController = navController,
        topBar = {
            CustomTopBar {
                Text(text = "Custom Top Bar")
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column{
            Text(text = "Entrega: ## ago - ## sep",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xffd5507c),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                //.padding(horizontal = 16.dp)
                .background(Color.White)
            ){

                Text(text = "Enviar a Fernanda Herrera, Calle ##### No. # Colonia",
                    fontSize = 14.sp,
                    //fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                )

                OrderStatus(status = "Pedido recibido")

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 10.dp, bottom = 10.dp)
                ){
                }

                Text(text = "Enviar a Fernanda Herrera, Calle ##### No. # Colonia",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


            }
        }
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
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            if(index >= currentStatus){
                                Color(0xffd5507c)
                            } else {
                                Color.LightGray
                            }
                        ),
                ){}

                Spacer(modifier = Modifier.width(4.dp))

                Text(text = s,
                    fontSize = 14.sp,
                    color = if(index >= currentStatus) Color(0xffd5507c) else Color.LightGray
                )
            }
        }
    }
}
