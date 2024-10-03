package mx.cazv.todasbrillamos.view.screens.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.GrayB3

@Composable
fun MinimalDialog(text: String, modifier: Modifier) {

    var showMoreInfo by remember { mutableStateOf(false) }

    Column (modifier = modifier
        .offset(x = 15.dp)
        .clickable { showMoreInfo = true}){
        Icon(painterResource(id = R.drawable.info_icon),
            contentDescription = "more info",
            tint = GrayB3
        )
    }

    if (showMoreInfo) {
        Dialog(onDismissRequest = { showMoreInfo = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardColors(BackgroundColor, Color.Black, Color.LightGray, Color.LightGray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(id = R.drawable.info_icon),
                        contentDescription = "more info",
                        tint = AccentColor,
                        modifier = Modifier.size(45.dp).padding(top = 3.dp, bottom = 16.dp)
                    )
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(bottom = 10.dp, start = 16.dp, end = 16.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}