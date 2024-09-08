package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import mx.cazv.todasbrillamos.ui.theme.AccentBlue

@Composable
fun Eye() {
    Box(
        modifier = Modifier.size(23.dp)
    ) {
        Icon(
            Icons.Outlined.RemoveRedEye,
            contentDescription = "Eye",
            tint = AccentBlue,
            modifier = Modifier
                .fillMaxSize()
        )

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidthPx = 7f
            val lineLength = size.width * 0.7f
            val startX = (size.width - lineLength) / 2
            val startY = (size.height - lineLength) / 2
            val endX = startX + lineLength
            val endY = startY + lineLength

            drawLine(
                color = AccentBlue,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidthPx
            )
        }
    }
}