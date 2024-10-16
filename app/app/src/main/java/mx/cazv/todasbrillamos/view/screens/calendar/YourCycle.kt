package mx.cazv.todasbrillamos.view.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.ColorOfMostFertilePeriod
import mx.cazv.todasbrillamos.ui.theme.OvulationColor
import mx.cazv.todasbrillamos.ui.theme.PeriodColor
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.PinkButton
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.view.screens.CalendarView
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.CalendarVM
import mx.cazv.todasbrillamos.viewmodel.NotificationsViewModel

/**
 * Archivo para mostrar el calendario del ciclo menstrual
 * @author Min Che Kim
 */


/**
 * Composable que muestra una leyenda con un color y un nombre.
 *
 * @param color El color de la leyenda.
 * @param name El nombre de la leyenda.
 */
@Composable
fun Group(color: Color, name: String) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = name,
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.W400,
        )
    }
}

/**
 * Pantalla que muestra el ciclo menstrual del usuario con un calendario y leyendas.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun YourCycle(
    navController: NavHostController,
    calendarVM: CalendarVM,
    authViewModel: AuthViewModel,
    notificationsViewModel: NotificationsViewModel
) {
    calendarVM.calculateCycle()
    val estado = calendarVM.state.collectAsState()
    var currentMonthDate by remember { mutableStateOf(estado.value.threeCycles.first().nextPeriodStartDate,) }
    val nextMonth = calendarVM.getNextMonth(currentMonthDate)
    val prevMonth = calendarVM.getPreviousMonth(currentMonthDate)

    println(estado.value.threeCycles.first().fertileDayEnd)
    val dates = calendarVM.generateCalendarDates(
        monthDate = currentMonthDate,
        threeCycles = estado.value.threeCycles
    )

    MainLayout(navController = navController, authViewModel, notificationsViewModel) {
        Column {
            Text(
                text = "Tu siguiente ciclo",
                fontSize = 29.sp,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor)
                    .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 30.dp)
            )

            CalendarView(
                month = currentMonthDate,
                date = dates,
                displayNext = true,
                displayPrev = true,
                onClickNext = { currentMonthDate = nextMonth },
                onClickPrev = { currentMonthDate = prevMonth },
                onClick = {  },
                startFromSunday = true,
                threeCycles = estado.value.threeCycles,
                modifier = Modifier.fillMaxWidth()
            )


            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Group(color = PeriodColor, name = "Periodo")
                Group(color = ColorOfMostFertilePeriod, name = "Periodo más fértil")
                Group(color = OvulationColor, name = "Ovulación")
            }

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                PinkButton("Volver a calcular", onClick = {
                    navController.navigate(Routes.ROUTE_CALENDAR)
                    calendarVM.updateSelectedDate(0L)
                    calendarVM.updateSelectedNumber("0", "period")
                    calendarVM.updateSelectedNumber("0", "cycle")})
            }

            Spacer(modifier = Modifier.height(40.dp))

        }
    }
}