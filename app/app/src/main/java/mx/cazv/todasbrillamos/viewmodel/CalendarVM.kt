package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.cazv.todasbrillamos.model.CalculatePeriod
import mx.cazv.todasbrillamos.model.YourCycleInfo

class CalendarVM: ViewModel() {

    private val calculatePeriod = CalculatePeriod()

    private val _state = MutableStateFlow(YourCycleInfo())
    val state: StateFlow<YourCycleInfo> = _state

    fun updateSelectedDate(value: Long){
        _state.value = _state.value.copy(date = value)
    }

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

    fun calculateCycle() {
        val date = state.value.date
        val period = state.value.period
        val cycle = state.value.cycle

        val nextPeriodStartDate = calculatePeriod.addDaysToDate(date, cycle.toInt())
        val nextPeriodEndDate = calculatePeriod.addDaysToDate(nextPeriodStartDate, period.toInt())

        val tempDate = calculatePeriod.addDaysToDate(nextPeriodStartDate, cycle.toInt())
        val ovulationDate = calculatePeriod.addDaysToDate(tempDate, -14)
        val fertileStart = calculatePeriod.addDaysToDate(ovulationDate, -4)
        val fertileEnd = calculatePeriod.addDaysToDate(ovulationDate, 2)

        _state.value = _state.value.copy(nextPeriodStartDate = nextPeriodStartDate)
        _state.value = _state.value.copy(nextPeriodEndDate = nextPeriodEndDate)
        _state.value = _state.value.copy(ovulationDate = ovulationDate)
        _state.value = _state.value.copy(fertileDayStart = fertileStart)
        _state.value = _state.value.copy(fertileDayEnd = fertileEnd)
    }


}