package mx.cazv.todasbrillamos.view.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import mx.cazv.todasbrillamos.view.formatToCalendarDay
import mx.cazv.todasbrillamos.view.formatToMonthAndYearString
import mx.cazv.todasbrillamos.view.getDayOfWeek
import mx.cazv.todasbrillamos.view.getDayOfWeek3Letters
import mx.cazv.todasbrillamos.view.getWeekDays
import mx.cazv.todasbrillamos.model.YourCycleInfo
import mx.cazv.todasbrillamos.ui.theme.ColorOfMostFertilePeriod
import mx.cazv.todasbrillamos.ui.theme.OvulationColor
import mx.cazv.todasbrillamos.ui.theme.PeriodColor
import java.util.Date

/**
 * Archivo que contiene los componentes para la vista del calendario con los datos del periodo menstrual.
 * @author Min Che Kim
 */

/**
 * Composable que representa una celda del calendario.
 *
 * @param date La fecha de la celda.
 * @param signal Indica si la celda debe estar resaltada.
 * @param onClick Acción a realizar al hacer clic en la celda.
 * @param signalColor El color de resaltado.
 * @param isPeriod Indica si la celda representa un día del periodo.
 * @param modifier Modificador para la celda.
 */
@Composable
private fun CalendarCell(
    date: Date,
    signal: Boolean,
    onClick: () -> Unit,
    signalColor: Color,
    isPeriod: Boolean,
    modifier: Modifier = Modifier,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(2.dp)
            .border(
                width = 2.dp,
                color = if (isPeriod) PeriodColor else Color.Transparent,
                shape = RoundedCornerShape(CornerSize(8.dp))
            )
            .background(
                shape = RoundedCornerShape(CornerSize(8.dp)),
                color = Color.Transparent,
            )
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(onClick = onClick)
    ) {
        if (signal) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(4.dp)
                    .background(
                        shape = CircleShape,
                        color = signalColor
                    )
            )
        }
        Text(
            text = text,
            color = colorScheme.onSecondaryContainer,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

/**
 * Composable que representa una celda de día de la semana.
 * @param weekday El día de la semana.
 * @param modifier Modificador para la celda.
 */
@Composable
private fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
    val text = weekday.getDayOfWeek3Letters()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
    ) {
        Text(
            text = text.orEmpty(),
            color = colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * Composable que representa la cuadrícula del calendario.
 *
 * @param date Lista de pares de fecha y booleano indicando si es un día especial.
 * @param onClick Acción a realizar al hacer clic en una celda.
 * @param startFromSunday Indica si la semana comienza en domingo.
 * @param threeCycles Lista de los ciclos menstruales del usuario.
 * @param modifier Modificador para la cuadrícula.
 */
@Composable
private fun CalendarGrid(
    date: ImmutableList<Pair<Date, Boolean>>,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    threeCycles: List<YourCycleInfo>,
    modifier: Modifier = Modifier
) {
    val weekdayFirstDay = date.first().first.getDayOfWeek()
    val weekdays = getWeekDays(startFromSunday)

    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        // Adds Spacers to align the first day of the month to the correct weekday
        repeat(if (!startFromSunday) weekdayFirstDay - 2 else weekdayFirstDay - 1) {
            Spacer(modifier = Modifier)
        }

        date.forEach {
            val currentDate = it.first

            val color = threeCycles.fold(Color.Transparent) { acc, cycle ->
                when (currentDate) {
                    cycle.ovulationDate -> OvulationColor
                    in cycle.fertileDayStart..cycle.fertileDayEnd -> ColorOfMostFertilePeriod
                    else -> acc
                }

            }
            println(it.second)

            // Color por defecto
            val isPeriod = threeCycles.any { cycle ->
                currentDate >= cycle.nextPeriodStartDate && currentDate < cycle.nextPeriodEndDate
            }

            CalendarCell(
                date = it.first,
                signal = it.second,
                onClick = { onClick(it.first) },
                signalColor = color,
                isPeriod
            )
        }
    }

}

/**
 * Composable que representa un diseño personalizado para la cuadrícula del calendario.
 *
 * @param modifier Modificador para el diseño.
 * @param horizontalGapDp Espacio horizontal entre celdas.
 * @param verticalGapDp Espacio vertical entre celdas.
 * @param content Contenido del diseño.
 */
@Composable
private fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = 2.dp,
    content: @Composable () -> Unit,
) {
    val horizontalGap = with(LocalDensity.current) {
        horizontalGapDp.roundToPx()
    }
    val verticalGap = with(LocalDensity.current) {
        verticalGapDp.roundToPx()
    }
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        val xPos: MutableList<Int> = mutableListOf()
        val yPos: MutableList<Int> = mutableListOf()
        var currentX = 0
        var currentY = 0
        measurables.forEach { _ ->
            xPos.add(currentX)
            yPos.add(currentY)
            if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                currentX = 0
                currentY += singleWidth + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
        }

        val placeables: List<Placeable> = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxHeight = singleWidth, maxWidth = singleWidth))
        }

        layout(
            width = constraints.maxWidth,
            height = currentY + singleWidth + verticalGap,
        ) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPos[index],
                    y = yPos[index],
                )
            }
        }
    }
}

/**
 * Composable que representa la vista del calendario.
 *
 * @param month El mes actual.
 * @param date Lista de pares de fecha y booleano indicando si es un día especial.
 * @param displayNext Indica si se debe mostrar el botón para el siguiente mes.
 * @param displayPrev Indica si se debe mostrar el botón para el mes anterior.
 * @param onClickNext Acción a realizar al hacer clic en el botón de siguiente mes.
 * @param onClickPrev Acción a realizar al hacer clic en el botón de mes anterior.
 * @param onClick Acción a realizar al hacer clic en una celda.
 * @param startFromSunday Indica si la semana comienza en domingo.
 * @param threeCycles Lista de los ciclos menstruales del usuario.
 * @param modifier Modificador para la vista del calendario.
 */
@Composable
fun CalendarView(
    month: Date,
    date: ImmutableList<Pair<Date, Boolean>>?,
    displayNext: Boolean,
    displayPrev: Boolean,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    threeCycles: List<YourCycleInfo>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (displayPrev)
                IconButton(
                    onClick = onClickPrev,
                    modifier = Modifier.align(Alignment.CenterStart),

                    ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = "navigate to previous month"
                    )
                }
            if (displayNext)
                IconButton(
                    onClick = onClickNext,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = "navigate to previous month"
                    )
                }
            Text(
                text = month.formatToMonthAndYearString(),
                style = typography.headlineMedium,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        if (!date.isNullOrEmpty()) {
            CalendarGrid(
                date = date,
                onClick = onClick,
                startFromSunday = startFromSunday,
                threeCycles,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
