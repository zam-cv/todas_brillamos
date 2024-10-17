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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.Section
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

/**
 * Archivo para mostrar información sobre la organización y su misión.
 * @author Carlos Zamudio, Jennyfer Jasso
 */

/**
 * Composable que muestra un texto alrededor de una imagen.
 *
 * @param text El texto a mostrar.
 * @param image El recurso de imagen a mostrar.
 */
//@Composable
//fun TextAroundImage(text: String , image: Int) {
//    val imageWidth = 80.dp
//    val imageHeight = 80.dp
//    val lineHeightInSp = 20.sp
//    val fontSizeInSp = 15.sp
//
//    // Variables para almacenar el ancho disponible para el texto
//    var maxCharsInLine by remember { mutableIntStateOf(0) }
//
//    val density = LocalDensity.current
//    val words = text.split(" ")
//
//    var textPart1 = ""
//    var textPart2 = text
//    var charsCount = 0
//
//    // Layout principal
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White)
//            .padding(10.dp)
//    ) {
//        Column {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(IntrinsicSize.Min) // Ajusta la altura mínima del Row a la de su contenido
//                    .onGloballyPositioned { coordinates ->
//                        // Calculamos el ancho disponible dinámicamente
//                        val totalWidth = coordinates.size.width
//                        val availableWidthForText = totalWidth - with(density) { imageWidth.toPx() }
//
//                        // Convertimos el tamaño de la fuente (Sp) a píxeles
//                        val fontSizeInPx = with(density) { fontSizeInSp.toPx() }
//
//                        // Estimación de cuántos caracteres caben en una línea
//                        maxCharsInLine = (availableWidthForText / (fontSizeInPx * 0.137f)).toInt()
//                    }
//            ) {
//                // Imagen en la izquierda
//                Image(
//                    painter = painterResource(id = image),
//                    contentDescription = "Imagen",
//                    modifier = Modifier
//                        .size(imageWidth, imageHeight)
//                        .padding(end = 10.dp),
//                    contentScale = ContentScale.Crop
//                )
//
//                // Primera parte del texto a la derecha de la imagen
//                if (maxCharsInLine > 0) {
//                    // Calculamos el texto que debe ir junto a la imagen
//                    for (word in words) {
//                        if (charsCount + word.length <= maxCharsInLine) {
//                            textPart1 += "$word "
//                            charsCount += word.length + 1
//                        } else {
//                            textPart2 = text.substring(textPart1.length)
//                            break
//                        }
//                    }
//                }
//
//                // Mostramos la primera parte del texto
//                Text(
//                    text = textPart1.trim(),
//                    fontSize = fontSizeInSp,
//                    //fontWeight = FontWeight.W300,
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxHeight(), // Asegura que ocupe toda la altura disponible
//                    lineHeight = lineHeightInSp
//                )
//            }
//
//            // Segunda parte del texto, debajo de la imagen
//            Text(
//                text = textPart2.trim(),
//                fontSize = fontSizeInSp,
//                //fontWeight = FontWeight.W300,
//                modifier = Modifier.fillMaxWidth(),
//                lineHeight = lineHeightInSp
//            )
//        }
//    }
//}

/**
 * Composable que muestra un texto alrededor de una imagen, pero acepta un AnnotatedString para el texto.
 *
 * @param text El texto a mostrar en formato AnnotatedString.
 * @param image El recurso de imagen a mostrar.
 */
@Composable
fun TextAroundImageAnnotatedString(text: AnnotatedString, image: Int) {
    val imageWidth = 80.dp
    val imageHeight = 80.dp
    val lineHeightInSp = 20.sp
    val fontSizeInSp = 15.sp

    // Variable para almacenar el ancho disponible para el texto
    var maxCharsInLine by remember { mutableIntStateOf(0) }

    val density = LocalDensity.current
    var textPart1 = AnnotatedString.Builder() // Primera parte del texto
    var textPart2 = AnnotatedString.Builder() // Segunda parte, será el resto del texto
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
                    .height(IntrinsicSize.Min)
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

                // Dividimos el texto respetando los estilos
                if (maxCharsInLine > 0) {
                    val textIterator = text.iterator()
                    while (textIterator.hasNext()) {
                        val char = textIterator.next()
                        textPart1.append(char)

                        // Actualizamos el conteo de caracteres
                        charsCount++

                        // Si alcanzamos el máximo de caracteres en la línea, guardamos el resto en textPart2
                        if (charsCount >= maxCharsInLine) {
                            while (textIterator.hasNext()) {
                                textPart2.append(textIterator.next())
                            }
                            break
                        }
                    }

                    // Aplicar los estilos (spans) a textPart1 y textPart2
                    text.spanStyles.forEach { rangeStyle ->
                        val start = rangeStyle.start
                        val end = rangeStyle.end

                        // Si el span está dentro del primer fragmento
                        if (end <= textPart1.length) {
                            textPart1.addStyle(rangeStyle.item, start, end)
                        }
                        // Si el span cruza ambas partes
                        else if (textPart1.length in (start + 1)..<end) {
                            textPart1.addStyle(rangeStyle.item, start, textPart1.length)
                            textPart2.addStyle(rangeStyle.item, 0, end - textPart1.length)
                        }
                        // Si el span solo está en el segundo fragmento
                        else {
                            textPart2.addStyle(rangeStyle.item, start - textPart1.length, end - textPart1.length)
                        }
                    }
                }

                // Mostramos la primera parte del texto
                Text(
                    text = textPart1.toAnnotatedString(),
                    fontSize = fontSizeInSp,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    lineHeight = lineHeightInSp
                )
            }

            // Mostramos la segunda parte del texto, debajo de la imagen
            if (textPart2.length > 0) {
                Text(
                    text = textPart2.toAnnotatedString(),
                    fontSize = fontSizeInSp,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = lineHeightInSp
                )
            }
        }
    }
}

/**
 * Pantalla de "Quiénes somos" que muestra información sobre la organización y su misión.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun About(navController: NavHostController) {
    CustomLayout(
        navController = navController,
        withStoreButton = true,
        topBar = {
            BasicTopBar(title = "Quiénes somos", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(BackgroundColor)
                //.verticalScroll(rememberScrollState())
        ) {
            Section("Todas brillamos") {
                TextAroundImageAnnotatedString(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Somos la Fundación Todas Brillamos AC, ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("una organización sin fines de lucro y Donataria Autorizada comprometida con el cambio positivo en la sociedad mexicana. ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Nuestro enfoque ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(" se centra en la igualdad de género, el empoderamiento de las mujeres y la erradicación de la pobreza menstrual. ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Nuestra misión ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(" es crear un entorno donde todas las personas, sin importar su género, puedan vivir con dignidad y libertad. Trabajamos incansablemente para promover la igualdad, empoderar a las mujeres y asegurar que todas tengan acceso a productos de higiene menstrual.")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" Únete a nosotros en esta importante causa y juntos hagamos brillar a todas las personas.")
                        }
                    },
                    image = mx.cazv.todasbrillamos.R.drawable.logo
                )
            }

            Section("Zazil") {
                TextAroundImageAnnotatedString(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("Zazil es una ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("marca")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(" comprometida con el")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" bienestar de las mujeres y el cuidado del medio ambiente. ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("Su ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" misión ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(" es proporcionar soluciones innovadoras y sostenibles para el período menstrual. ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("¿Cómo lo hacen? ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("A través de la creación de toallas femeninas reutilizables. ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("Zazil ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("diseña toallas con materiales de alta calidad, hipoalergénicos y absorbentes, ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("que garantizan una experiencia cómoda y segura durante el período menstrual. Pero lo más importante es que son ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("reutilizables ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(", lo que significa que ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("ayudan a reducir la generación de residuos y contribuyen a la conservación del medio ambiente.")
                        }
                    },
                    image = mx.cazv.todasbrillamos.R.drawable.zazil_logo
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}