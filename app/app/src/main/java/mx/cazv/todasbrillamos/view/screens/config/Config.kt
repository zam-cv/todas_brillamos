package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.CustomTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun Option(title: String, icon: ImageVector) {
    Column (
        modifier = Modifier
            .padding()
            .background(Color.White)
            .drawBehind {
                val strokeWidth = 0.5.dp.toPx()
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 15.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(22.dp)
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(title, fontWeight = FontWeight.W400)
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(43.dp)
                        .graphicsLayer(
                            scaleX = 0.6f,
                            scaleY = 1f
                        )
                )
            }
        }
    }
}

@Composable
fun OptionsSection(options: List<Pair<String, ImageVector>>) {
    Column (
        modifier = Modifier
            .padding(top = 20.dp)
    ) {
        options.forEach {
            Option(title = it.first, icon = it.second)
        }
    }
}

@Composable
fun Config(navController: NavHostController) {
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
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .background(BackgroundColor)
        ) {
            OptionsSection(
                options = listOf(
                    Pair("Métodos de pago", Icons.Filled.CreditCard),
                    Pair("Notificaciones", Icons.Outlined.Notifications),
                    Pair("Privacidad & Seguridad", Icons.Outlined.Lock)
                )
            )

            OptionsSection(
                options = listOf(
                    Pair("Quienes somos", Icons.Outlined.QuestionMark),
                    Pair("Terminos y condiciones", Icons.AutoMirrored.Outlined.InsertDriveFile),
                    Pair("Compartir esta aplicación", Icons.Filled.Share)
                )
            )

            OptionsSection(
                options = listOf(
                    Pair("Cambiar cuenta", Icons.Outlined.Person),
                    Pair("Cerrar sesión", Icons.AutoMirrored.Filled.ExitToApp)
                )
            )
        }
    }
}