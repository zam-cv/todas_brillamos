package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.CustomTopBar
import mx.cazv.todasbrillamos.view.components.Section
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun TextAroundImage(text: String, image: Int) {
    val imageWidth = 80.dp
    val imageHeight = 80.dp
    val lineHeightInSp = 20.sp
    val fontSizeInSp = 16.sp

    // Variables para almacenar el ancho disponible para el texto
    var maxCharsInLine by remember { mutableIntStateOf(0) }

    val density = LocalDensity.current
    val words = text.split(" ")

    var textPart1 = ""
    var textPart2 = text
    var charsCount = 0

    // Layout principal
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // Ajusta la altura mínima del Row a la de su contenido
                    .onGloballyPositioned { coordinates ->
                        // Calculamos el ancho disponible dinámicamente
                        val totalWidth = coordinates.size.width
                        val availableWidthForText = totalWidth - with(density) { imageWidth.toPx() }

                        // Convertimos el tamaño de la fuente (Sp) a píxeles
                        val fontSizeInPx = with(density) { fontSizeInSp.toPx() }

                        // Estimación de cuántos caracteres caben en una línea
                        maxCharsInLine = (availableWidthForText / (fontSizeInPx * 0.137f)).toInt()
                    }
            ) {
                // Imagen en la izquierda
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Imagen",
                    modifier = Modifier
                        .size(imageWidth, imageHeight)
                        .padding(end = 10.dp),
                    contentScale = ContentScale.Crop
                )

                // Primera parte del texto a la derecha de la imagen
                if (maxCharsInLine > 0) {
                    // Calculamos el texto que debe ir junto a la imagen
                    for (word in words) {
                        if (charsCount + word.length <= maxCharsInLine) {
                            textPart1 += "$word "
                            charsCount += word.length + 1
                        } else {
                            textPart2 = text.substring(textPart1.length)
                            break
                        }
                    }
                }

                // Mostramos la primera parte del texto
                Text(
                    text = textPart1.trim(),
                    fontSize = fontSizeInSp,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(), // Asegura que ocupe toda la altura disponible
                    lineHeight = lineHeightInSp
                )
            }

            // Segunda parte del texto, debajo de la imagen
            Text(
                text = textPart2.trim(),
                fontSize = fontSizeInSp,
                fontWeight = FontWeight.W300,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = lineHeightInSp
            )
        }
    }
}


@Composable
fun About(navController: NavHostController) {
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
                //.verticalScroll(rememberScrollState())
        ) {
            Section("Todas brillamos") {
                TextAroundImage(
                    text = "Somos la Fundación Todas Brillamos AC, una organización sin fines de lucro y Donataria Autorizada comprometida con el cambio positivo en la sociedad mexicana. Nuestro enfoque se centra en la igualdad de género, el empoderamiento de las mujeres y la erradicación de la pobreza menstrual. Nuestra misión es crear un entorno donde todas las personas, sin importar su género, puedan vivir con dignidad y libertad. Trabajamos incansablemente para promover la igualdad, empoderar a las mujeres y asegurar que todas tengan acceso a productos de higiene menstrual. Únete a nosotros en esta importante causa y juntos hagamos brillar a todas las personas.",
                    image = mx.cazv.todasbrillamos.R.drawable.logo
                )
            }

            Section("Todas brillamos") {
                TextAroundImage(
                    text = "Zazil es una marca comprometida con el bienestar de las mujeres y el cuidado del medio ambiente. Su misión es proporcionar soluciones innovadoras y sostenibles para el período menstrual. ¿Cómo lo hacen? A través de la creación de toallas femeninas reutilizables. Zazil diseña toallas con materiales de alta calidad, hipoalergénicos y absorbentes, que garantizan una experiencia cómoda y segura durante el período menstrual. Pero lo más importante es que son reutilizables, lo que significa que ayudan a reducir la generación de residuos y contribuyen a la conservación del medio ambiente.",
                    image = mx.cazv.todasbrillamos.R.drawable.zazil_logo
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}