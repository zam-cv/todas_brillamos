package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.GrayB3
import mx.cazv.todasbrillamos.view.components.DropDownMenu
import mx.cazv.todasbrillamos.view.components.PinkButton
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.view.layouts.MainLayout

@Composable
fun Calendar(navController: NavHostController) {
    val period = (1..10).toList()
    val cycle = (21..40).toList()
    MainLayout(navController = navController) {
        Column (modifier = Modifier){
            Box (modifier = Modifier.fillMaxWidth()){
                Image(painter = painterResource(id = R.drawable.circle_deco),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth())

                Image(painter = painterResource(id = R.drawable.calendar_ic),
                    contentDescription = null,
                    modifier = Modifier.size(width = 500.dp, height =200.dp))

                Text(text = "Calcula tu periodo\nmenstrual",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 25.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            DatePickerDocked()

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(period.map { it.toString() }, "period")

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(cycle.map { it.toString() }, "cycle")

            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)){
                PinkButton(text = "Calcular mi periodo")
            }
            
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Los resultados de esta calculadora de periodos pueden no ser 100% precisos y eso se debe a que cada cuerpo y cada ciclo es diferente.",
                maxLines = 3,
                style = TextStyle(
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                ),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp))

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    val navController = rememberNavController()
    Calendar(navController)
}