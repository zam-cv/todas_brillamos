package mx.cazv.todasbrillamos.view.screens.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.components.DropDownMenu
import mx.cazv.todasbrillamos.view.components.PinkButton
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.viewmodel.CalendarVM

@Composable
fun Calendar(navController: NavHostController, calendarVM: CalendarVM = viewModel()) {

    val period = (1..10).toList()
    val cycle = (21..40).toList()

//    val estado = calendarVM.estado.collectAsState()
//
//    val model = CalculatePeriod()
//
//    val nextPeriodStartDate = model.convertMillisToDate(estado.value.nextPeriodStartDate)
//    val nextPeriodEndDate = model.convertMillisToDate(estado.value.nextPeriodEndDate)
//    val ovulationDate = model.convertMillisToDate(estado.value.ovulationDate)
//    val fertileDayStart = model.convertMillisToDate(estado.value.fertileDayStart)
//    val fertileDayEnd = model.convertMillisToDate(estado.value.fertileDayEnd)



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

            DatePickerDocked("La fecha en que comenzaste tu último periodo y seguiste sangrando (en vez de solo manchar)")

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(period.map { it.toString() },
                "period",
                "Saca un promedio aproximado de cuánto tiempo a durado tu periodo durante los últimos 3 meses.")

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(cycle.map { it.toString() },
                "cycle",
                "Se trata del tiempo entre el comienzo de un periodo y el comienzo del siguiente. Toma un promedio aproximado.")

            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)){
                PinkButton(text = "Calcular mi periodo", onClick = {
                        // Pasar los datos al ViewModel para hacer los cálculos
                    if (calendarVM.state.value.date != 0L &&
                        calendarVM.state.value.period != "0" &&
                        calendarVM.state.value.cycle != "0") {

                        calendarVM.calculateCycle()
                    }

                        // Imprime los resultados en la consola
                        // Navegar a la pantalla YourCycle
                        //navController.navigate("your_cycle")

                })
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
//            Text("Siguiente menstruación: $nextPeriodStartDate a $nextPeriodEndDate")
//            Text("Fecha de ovulación: $ovulationDate")
//            Text("Días fértiles: $fertileDayStart a $fertileDayEnd")
//
//            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    val navController = rememberNavController()
    Calendar(navController)
}

//fun CalculatePeriod(period: Int, cycle: Int): Int {
//
//}