package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.ui.theme.Stroke

@Composable
fun Input(
    placeholder: String,
    suffixIcon : @Composable (() -> Unit)? = null,
    imageId: Int? = null,
    height: Int? = null,
    padding: Dp = 20.dp,
    imageSize: Dp = 24.dp,
    topStart: Dp = 10.dp,
    topEnd: Dp = 10.dp,
    bottomEnd: Dp = 10.dp,
    bottomStart: Dp = 10.dp,
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    val shape = RoundedCornerShape(topStart,topEnd,bottomEnd,bottomStart)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .let { modifier ->
                if (height != null) {
                    modifier.height(height.dp)
                } else {
                    modifier
                }
            }
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Stroke,
                shape = shape
            )
            .padding(start = 10.dp, end = 10.dp, bottom = padding, top = padding),

        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    } else {
                        innerTextField()
                    }
                }

                if (suffixIcon != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 3.dp)
                    ) {
                        suffixIcon()
                    }
                }
                if (imageId != null) {
                    val image = painterResource(id = imageId)
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(imageSize))
                }
            }
        }
    )
}