package mx.cazv.todasbrillamos.view.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Post(){
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xfff4d0cb)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "TITULO",
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = "Los tampones contienen plomo, arsénico y sustancias químicas " +
                        "que pueden causar enfermedades.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextButton(
                onClick = {},
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Leer más",
                        textDecoration = TextDecoration.Underline,
                        color = Color.Black
                    )
                }
            }
        }
    }
}