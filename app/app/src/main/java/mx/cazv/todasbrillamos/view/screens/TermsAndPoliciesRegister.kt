package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Description
import mx.cazv.todasbrillamos.view.components.Section
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

/**
 * Archivo para mostrar los términos y políticas legales de la organización.
 * @author Carlos Zamudio
 *
 */

/**
 * Composable que muestra una sección con un título y contenido.
 *
 * @param title El título de la sección.
 * @param content El contenido de la sección.
 */
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

/**
 * Composable que muestra una sección con un título y una lista de elementos.
 *
 * @param title El título de la sección.
 * @param list La lista de elementos a mostrar.
 */
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

/**
 * Pantalla de términos y políticas legales que muestra la información de privacidad y uso de datos.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun TermsAndPoliciesRegister(navController: NavHostController) {
    CustomLayout(
        navController = navController,
        withStoreButton = false,
        topBar = {
            BasicTopBar(title = "Términos y políticas legales", navController = navController)
        },
        bottomBar = { },
    ) {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(BackgroundColor)
                //.verticalScroll(rememberScrollState())
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