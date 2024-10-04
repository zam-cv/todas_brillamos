package mx.cazv.todasbrillamos.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalculatePeriod {

    fun addDaysToDate(millis: Long, days: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.timeInMillis
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(java.util.Date(millis))
    }

}