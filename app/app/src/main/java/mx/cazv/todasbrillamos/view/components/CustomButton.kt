package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun CustomButton(text: String, col: Color, colT: Color = Color.White){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(col, shape = RoundedCornerShape(10.dp))
    ) {
        OutlinedButton(
            onClick = {},
            border = null,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),

        ) {
            Text(
                text = text,
                color =colT,
                fontWeight = FontWeight.W400,
                fontSize = 17.sp
            )
        }
    }
}