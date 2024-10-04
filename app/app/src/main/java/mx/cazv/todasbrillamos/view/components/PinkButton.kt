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
import mx.cazv.todasbrillamos.ui.theme.Pink

@Composable
fun PinkButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Pink, shape = RoundedCornerShape(10.dp))
    ) {
        OutlinedButton(
            onClick = { onClick()},
            border = null,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 17.sp
            )
        }
    }
}