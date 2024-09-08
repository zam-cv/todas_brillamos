package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.ui.theme.ButtonColor

@Composable
fun Button(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonColor, shape = RoundedCornerShape(30.dp))
    ) {
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.W400,
                fontSize = 17.sp
            )
        }
    }
}