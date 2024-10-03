package mx.cazv.todasbrillamos.view.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.components.InteractiveCard

@Preview
@Composable
fun InteractiveCardsHome(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(385.dp)
        ){
            InteractiveCard(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("Pregunta \na la ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("IA")
                    }
                },
                image = R.drawable.med_ic,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(0.48f)
                    .height(160.dp),
                backgroundColor = Color(0xffd5507c),
                imageSize = 150,
                imageAlignment = Alignment.BottomEnd,
                valx = 30.dp
            )

            InteractiveCard(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("Calcula ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("tu ciclo \nmenstrual")

                    }
                },
                image = R.drawable.calendar_ic,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxWidth(0.48f)
                    .height(245.dp),
                backgroundColor = Color(0xfff4d0cb),
                imageSize = 200,
                imageAlignment = Alignment.Center,
                valy = 20.dp,
            )


            InteractiveCard(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Consulta \nnuestro \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Instructivo de \nlavado")
                        }
                    },
                    image = R.drawable.instructivo_ic,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(0.48f)
                        .height(210.dp),
                    backgroundColor = Color(0xfff4d0cb),
                    imageSize = 100,
                    imageAlignment = Alignment.BottomEnd
                )

                InteractiveCard(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Visita \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("nuestra \ntienda")
                        }
                    },
                    image = R.drawable.tienda_ic,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth(0.48f)
                        .height(120.dp),
                    backgroundColor = Color(0xffd5507c),
                    imageSize = 150,
                    imageAlignment = Alignment.CenterEnd,
                    valx = 30.dp
                )
        }
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ){
//            InteractiveCard(
//                text = buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
//                        append("Pregunta \na la ")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontStyle = FontStyle.Italic
//                        )
//                    ) {
//                        append("IA")
//                    }
//                },
//                image = R.drawable.med_ic,
//                modifier = Modifier
//                    .weight(1f)
//                    .height(170.dp),
//                backgroundColor = Color(0xffd5507c),
//                imageSize = 150,
//                imageAlignment = Alignment.BottomEnd,
//                valx = 30.dp
//            )
//
//            InteractiveCard(
//                text = buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
//                        append("Calcula ")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontStyle = FontStyle.Italic
//                        )
//                    ) {
//                        append("tu ciclo \nmenstrual")
//
//                    }
//                },
//                image = R.drawable.calendar_ic,
//                modifier = Modifier
//                    .weight(1f)
//                    .height(220.dp),
//                backgroundColor = Color(0xfff4d0cb),
//                imageSize = 150,
//                imageAlignment = Alignment.Center,
//                valy = 20.dp,
//            )
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ){
//            InteractiveCard(
//                text = buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
//                        append("Consulta \nnuestro \n")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontStyle = FontStyle.Italic
//                        )
//                    ) {
//                        append("Instructivo de \nlavado")
//                    }
//                },
//                image = R.drawable.instructivo_ic,
//                modifier = Modifier
//                    .weight(1f)
//                    .height(200.dp),
//                backgroundColor = Color(0xfff4d0cb),
//                imageSize = 100,
//                imageAlignment = Alignment.BottomEnd
//            )
//
//            InteractiveCard(
//                text = buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
//                        append("Visita \n")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontStyle = FontStyle.Italic
//                        )
//                    ) {
//                        append("nuestra \ntienda")
//                    }
//                },
//                image = R.drawable.tienda_ic,
//                modifier = Modifier
//                    .weight(1f)
//                    .height(110.dp),
//                backgroundColor = Color(0xffd5507c),
//                imageSize = 150,
//                imageAlignment = Alignment.CenterEnd,
//                valx = 30.dp
//            )
//        }
    }
}
