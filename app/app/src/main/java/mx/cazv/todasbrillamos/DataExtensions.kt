package mx.cazv.todasbrillamos

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Archivo que contiene las extensiones para formatear fechas y obtener información de fechas.
 * @author Min Che Kim
 */

/**
 * Extensión para formatear una fecha a un día del calendario.
 * @return El día del mes como cadena.
 */
fun Date.formatToCalendarDay(): String =
    SimpleDateFormat("d", Locale.getDefault()).format(this)

/**
 * Extensión para obtener el nombre del día de la semana en tres letras.
 * @return El nombre del día de la semana como cadena.
 */
fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())


/**
 * Extensión para obtener el día de la semana de una fecha.
 * @return El día de la semana como entero (1 para domingo, 7 para sábado).
 */
fun Date.getDayOfWeek(): Int {
    val calendar = Calendar.getInstance().apply { time = this@getDayOfWeek }
    return calendar.get(Calendar.DAY_OF_WEEK) // Returns a value between 1 (Sunday) to 7 (Saturday)
}

/**
 * Extensión para obtener los días de la semana en orden, comenzando desde domingo o lunes.
 * @param startFromSunday Indica si la semana comienza en domingo.
 * @return Lista inmutable de días de la semana.
 */
fun getWeekDays(startFromSunday: Boolean): ImmutableList<Int> {
    val lista = (1..7).toList()
    return (if (startFromSunday) lista else lista.drop(1) + lista.take(1)).toImmutableList()
}

/**
 * Extensión para formatear una fecha a una cadena con el mes y el año.
 * @return El mes y el año como cadena.
 */
fun Date.formatToMonthAndYearString(): String = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(this)
