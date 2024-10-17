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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.DropDownMenu
import mx.cazv.todasbrillamos.view.components.PinkButton
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.CalendarVM
import mx.cazv.todasbrillamos.viewmodel.NotificationsViewModel

/**
 * Composable que muestra la pantalla inicial para el calculo del periodo menstrual
 * @author Min Che Kim
 *
 * @param navController El controlador de navegación.
 * @param calendarVM El ViewModel del calendario.
 */
@Composable
fun Calendar(
    navController: NavHostController,
    calendarVM: CalendarVM,
    authViewModel: AuthViewModel,
    notificationsViewModel: NotificationsViewModel
) {
    val period = (1..10).toList()
    val cycle = (21..40).toList()

    val estado = calendarVM.state.collectAsState()

    MainLayout(navController = navController, authViewModel, notificationsViewModel) {
        Column(modifier = Modifier) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.circle_deco),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth())

                Image(painter = painterResource(id = R.drawable.calendar_ic),
                    contentDescription = null,
                    modifier = Modifier.size(width = 500.dp, height =200.dp))

                Text(
                    text = "Calcula tu siguiente\nperiodo menstrual",
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

            DatePickerDocked("La fecha en que comenzaste tu último periodo y seguiste sangrando (en vez de solo manchar)",
                calendarVM)

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(
                period.map { it.toString() },
                "period",
                "Saca un promedio aproximado de cuánto tiempo a durado tu periodo durante los últimos 3 meses.",
                calendarVM
            )

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(
                cycle.map { it.toString() },
                "cycle",
                "Se trata del tiempo entre el comienzo de un periodo y el comienzo del siguiente. Toma un promedio aproximado.",
                calendarVM
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                PinkButton(text = "Calcular mi periodo", onClick = {
                    // Pasar los datos al ViewModel para hacer los cálculos
                    if (estado.value.date != 0L &&
                        estado.value.period != "0" &&
                        estado.value.cycle != "0"
                    ) {
                        navController.navigate(Routes.ROUTE_YOUR_CYCLE)
                    }
                })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Los resultados de esta calculadora de periodos pueden no ser 100% precisos y eso se debe a que cada cuerpo y cada ciclo es diferente. Si tiene dudas más específicas de su ciclo menstrual, consulte a un especialista.",
                maxLines = 4,
                style = TextStyle(
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                ),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}