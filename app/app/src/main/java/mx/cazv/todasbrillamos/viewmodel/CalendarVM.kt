package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.cazv.todasbrillamos.model.CalendarModel
import mx.cazv.todasbrillamos.model.YourCycleInfo
import java.util.Calendar
import java.util.Date

/**
 * ViewModel para manejar la lógica del calendario y los cálculos del ciclo menstrual.
 * @author Min Che Kim
 */
class CalendarVM: ViewModel() {

    private val calendarModel = CalendarModel()

    private val _state = MutableStateFlow(YourCycleInfo())
    val state: StateFlow<YourCycleInfo> = _state

    /**
     * Actualiza la fecha seleccionada en el estado.
     * @param value La nueva fecha en milisegundos.
     */
    fun updateSelectedDate(value: Long){
        _state.value = _state.value.copy(date = value)
    }

    /**
     * Actualiza el número seleccionado (periodo o ciclo) en el estado.
     * @param value El nuevo valor como cadena.
     * @param type El tipo de valor que se está actualizando ("period" o "cycle").
     */
    fun updateSelectedNumber(value: String, type: String){
        if (type == "period")
        {
            _state.value = _state.value.copy(period = value)
        }
        else if (type == "cycle")
        {
            _state.value = _state.value.copy(cycle = value)
        }
    }

    /**
     * Calcula las fechas del ciclo basadas en la fecha seleccionada, el periodo y el ciclo.
     */
    fun calculateCycle() {
        val date = state.value.date
        val period = state.value.period.toInt()
        val cycle = state.value.cycle.toInt()

        val cycles = mutableListOf<YourCycleInfo>()

        var currentStartDate = Date(date)

        for (i in 1..3) {
            val nextPeriodStartDate = if (i == 1) currentStartDate else calendarModel.addDaysToDate(currentStartDate, cycle)
            val nextPeriodEndDate = calendarModel.addDaysToDate(nextPeriodStartDate, period)

            val tempDate = calendarModel.addDaysToDate(nextPeriodStartDate, cycle)
            val ovulationDate = calendarModel.addDaysToDate(tempDate, -14)
            val fertileStart = calendarModel.addDaysToDate(ovulationDate, -4)
            val fertileEnd = calendarModel.addDaysToDate(ovulationDate, 2)

            cycles.add(
                YourCycleInfo(
                    date = nextPeriodStartDate.time,
                    period = period.toString(),
                    cycle = cycle.toString(),
                    nextPeriodStartDate = nextPeriodStartDate,
                    nextPeriodEndDate = nextPeriodEndDate,
                    ovulationDate = ovulationDate,
                    fertileDayStart = fertileStart,
                    fertileDayEnd = fertileEnd
                )
            )

            currentStartDate = nextPeriodStartDate
        }

        // Actualiza el estado con la información de los tres ciclos
        _state.value = _state.value.copy(
            threeCycles = cycles
        )

//        val nextPeriodStartDate = calendarModel.addDaysToDate(Date(date), cycle.toInt()+1)
//        val nextPeriodEndDate = calendarModel.addDaysToDate(nextPeriodStartDate, period.toInt())
//
//        val tempDate = calendarModel.addDaysToDate(nextPeriodStartDate, cycle.toInt())
//        val ovulationDate = calendarModel.addDaysToDate(tempDate, -14)
//        val fertileStart = calendarModel.addDaysToDate(ovulationDate, -4)
//        val fertileEnd = calendarModel.addDaysToDate(ovulationDate, 2)
//
//        _state.value = _state.value.copy(nextPeriodStartDate = nextPeriodStartDate)
//        _state.value = _state.value.copy(nextPeriodEndDate = nextPeriodEndDate)
//        _state.value = _state.value.copy(ovulationDate = ovulationDate)
//        _state.value = _state.value.copy(fertileDayStart = fertileStart)
//        _state.value = _state.value.copy(fertileDayEnd = fertileEnd)
    }

    /**
     * Genera una lista de fechas para el mes dado, marcando los días especiales.
     *
     * @param monthDate La fecha que representa el mes para generar las fechas.
     * @param nextPeriodStartDate La fecha de inicio del próximo periodo.
     * @param nextPeriodEndDate La fecha de fin del próximo periodo.
     * @param ovulationDate La fecha de ovulación.
     * @param fertileStartDate La fecha de inicio del periodo fértil.
     * @param fertileEndDate La fecha de fin del periodo fértil.
     * @return Una lista de pares donde cada par contiene una fecha y un booleano que indica si es un día especial.
     */
    fun generateCalendarDates(
        monthDate: Date,
//        nextPeriodStartDate: Date,
//        nextPeriodEndDate: Date,
//        ovulationDate: Date,
//        fertileStartDate: Date,
//        fertileEndDate: Date
        threeCycles: List<YourCycleInfo>
    ): ImmutableList<Pair<Date, Boolean>> {
        val datesList = mutableListOf<Pair<Date, Boolean>>()


        val calendar = Calendar.getInstance().apply {
            time = monthDate
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val currentDate = calendar.time

            val isSpecialDay = threeCycles.any { cycle ->
                currentDate == cycle.ovulationDate ||
                        currentDate >= cycle.fertileDayStart && currentDate <= cycle.fertileDayEnd

            }

//            val isSpecialDay = (
//                    (currentDate >= (nextPeriodStartDate) && currentDate < (nextPeriodEndDate)) ||
//                            currentDate == ovulationDate ||
//                            currentDate >= (fertileStartDate) && currentDate <= (fertileEndDate)
//                    )

            datesList.add(currentDate to isSpecialDay)
        }

        return datesList.toImmutableList()
    }

    /**
     * Obtiene la fecha del próximo mes basada en la fecha del mes actual.
     *
     * @param currentMonthDate La fecha del mes actual.
     * @return La fecha del próximo mes.
     */
    fun getNextMonth(currentMonthDate: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = currentMonthDate
            add(Calendar.MONTH, 1)
        }
        return calendar.time
    }

    /**
     * Obtiene la fecha del mes anterior basada en la fecha del mes actual.
     *
     * @param currentMonthDate La fecha del mes actual.
     * @return La fecha del mes anterior.
     */
    fun getPreviousMonth(currentMonthDate: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = currentMonthDate
            add(Calendar.MONTH, -1)
        }
        return calendar.time
    }

}