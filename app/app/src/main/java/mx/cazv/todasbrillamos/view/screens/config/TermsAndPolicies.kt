package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.Button
import mx.cazv.todasbrillamos.view.components.CustomTopBar
import mx.cazv.todasbrillamos.view.components.Description
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.components.Line
import mx.cazv.todasbrillamos.view.components.Section
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun SectionWithContent(title: String, content: String) {
    Section(
        title = title
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = content,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun SectionWithList(title: String, list: List<String>) {
    Section(
        title = title
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 20.dp, bottom = 20.dp, end = 10.dp)
        ) {
            list.forEach {
                Description(text = it)
            }
        }
    }
}

@Composable
fun TermsAndPolicies(navController: NavHostController) {
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
                .fillMaxHeight()
                .fillMaxWidth()
                .background(BackgroundColor)
                .verticalScroll(rememberScrollState())
        ) {
            SectionWithContent(
                title = "AVISO DE PRIVACIDAD",
                content = "En Fundación Todas Brillamos AC, valoramos la privacidad de nuestros clientes y nos comprometemos a proteger la información personal que nos proporcionan. Esta política de privacidad explica cómo recopilamos, utilizamos y protegemos sus datos personales."
            )

            SectionWithList(
                title = "INFORMACIÓN RECOLECTADA",
                list = listOf(
                    "Datos personales: nombre, dirección, correo electrónico, número de teléfono",
                    "Información de pago: tarjeta de crédito, débito o PayPal",
                )
            )

            SectionWithList(
                title = "USO DE LA INFORMACIÓN",
                list = listOf(
                    "Procesar y enviar pedidos",
                    "Enviar correos electrónicos con promociones y ofertas especiales",
                    "Mejorar nuestra tienda online y experiencia de usuario"
                )
            )

            SectionWithList(
                title = "PROTECCIÓN DE LA INFORMACIÓN",
                list = listOf(
                    "Utilizamos medidas de seguridad para proteger sus datos personales",
                    "No compartimos información personal con terceros, excepto para procesar pedidos y envíos"
                )
            )

            SectionWithList(
                title = "DERECHOS DE LOS CLIENTES",
                list = listOf(
                    "Acceder, rectificar o cancelar su información personal en cualquier momento",
                    "Oponerse al uso de su información para fines de marketing"
                )
            )

            SectionWithList(
                title = "CAMBIOS EN LA POLÍTICA DE PRIVACIDAD",
                list = listOf(
                    "Podemos actualizar esta política de privacidad en cualquier momento",
                    "Se notificará a los clientes de cualquier cambio significativo"
                )
            )

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp)
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 0.dp, bottom = 0.dp, start = 10.dp, end = 10.dp)
                ) {
                    Column {
                        Text(
                            text = "FECHA DE ÚLTIMA ACTUALIZACIÓN:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        )

                        Text(
                            text = "Lunes 2 de Septiembre 2024",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 30.dp)
                        )

                        Text(
                            text = "Si tienes alguna pregunta o inquietud, por favor no dudes en contactarnos.",
                            fontSize = 13.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(25.dp))
        }
    }
}