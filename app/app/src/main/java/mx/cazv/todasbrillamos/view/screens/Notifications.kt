package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.BadgeColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.CustomTopBar
import mx.cazv.todasbrillamos.view.components.Line
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun Date(value: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 13.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Line()
        }

        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BadgeColor)
                .padding(start = 10.dp, end = 10.dp)
                .wrapContentWidth()

        ) {
            Text(text = value, fontSize = 14.sp)
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Line()
        }
    }
}

@Composable
fun Notification(
    title: String,
    description: String,
    time: String,
    withLine: Boolean = true
) {
    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, end = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 10.dp, end = 13.dp)
            ) {
                Icon(
                    Icons.Outlined.NotificationsActive,
                    contentDescription = "Notification",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }

            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                    ) {
                        Text(time,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                }

                Text(description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        if (withLine) {
            Box(modifier = Modifier.padding(top = 15.dp)) {
                Line(height = 0.5.dp)
            }
        }
    }
}

@Composable
fun Notifications(navController: NavHostController) {
    CustomLayout (
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
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 25.dp)
        ) {
            Date("Hoy")

            Notification(
                title = "¡Oferta exclusiva!",
                description = "Aprovecha un 20% de descuento en tu próxima compra. válido hasta el domingo.",
                time = "2:16 PM"
            )

            Notification(
                title = "Calendario Menstrual",
                description = "¡Descubre nuestra nueva función! Ahora puedes seguir tus ciclos de manera personalizada.",
                time = "11:30 AM",
                withLine = false
            )

            Date("Ayer")

            Notification(
                title = "¡Novedad en la tienda!",
                description = "Descubre nuestros nuevos productos recién llegados. No te lo pierdas.",
                time = "8:05 PM"
            )

            Notification(
                title = "Envío",
                description = "Buenas noticias! Tu pedido #1234 ha sido enviado. Estará en tus manos pronto.",
                time = "3:50 PM"
            )

            Notification(
                title = "Gracias por tu compra",
                description = "Tu compra fue realizada con éxito. Gracias por comprar con nosotros.",
                time = "9:30 AM",
                withLine = false
            )

            Date("Ago 17")

            Notification(
                title = "Producto favorito en oferta",
                description = "Un producto de tu lista de deseos tiene descuento. ¡Cómpralo ahora!",
                time = "7:22 PM"
            )

            Notification(
                title = "Checa este nuevo artículo",
                description = "'Toallas sanitarias para frenar la contaminación'. Léelo ahora y descubre una opción más sostenible para tu cuidado personal.",
                time = "5:26 PM"
            )

            Notification(
                title = "Carrito abandonado",
                description = "Los artículos en tu carrito están esperando por ti. Completa tu compra ahora.",
                time = "11:42 AM",
                withLine = false
            )
        }
    }
}