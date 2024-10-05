package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.R

/**
 * Función composable que muestra la información de un pedido.
 * @author Jennyfer Jasso
 */
@Composable
fun InfoOrder() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .horizontalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 8.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(top = 22.dp, start = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "MX $1100.25 \n4 artículos",
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    lineHeight = 14.sp
                )
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
        ){
            Text(text = "Septiembre xx, xxxx, hh:mm am",
                fontSize = 14.sp,
                color = Color(0xffd5507c),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(top = 4.dp))

            androidx.compose.material3.Button(
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xffd5507c)),
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(text = "Rastreo")
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp)
    }
}