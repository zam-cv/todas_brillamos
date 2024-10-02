package mx.cazv.todasbrillamos.view.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.components.InteractiveCard

@Composable
fun InteractiveCardsHome(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                    .weight(1f)
                    .height(170.dp),
                backgroundColor = Color(0xffd5507c),
                imageSize = 140,
                imageAlignment = Alignment.BottomEnd
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
                    .weight(1f)
                    .height(220.dp),
                backgroundColor = Color(0xfff4d0cb),
                imageSize = 150,
                imageAlignment = Alignment.Center
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
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
                    .weight(1f)
                    .height(170.dp),
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
                    .weight(1f)
                    .height(110.dp),
                backgroundColor = Color(0xffd5507c),
                imageSize = 150,
                imageAlignment = Alignment.CenterEnd
            )
        }
    }
}
