package mx.cazv.todasbrillamos.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Modelo para manejar las operaciones relacionadas con el calendario.
 * @author Min Che Kim
 */
class CalendarModel {

    /**
     * Suma un número especificado de días a una fecha dada.
     * @param date La fecha original.
     * @param days El número de días a sumar.
     * @return La nueva fecha con los días sumados.
     */
    fun addDaysToDate(date: Date, days: Int): Date {

        val calendar = Calendar.getInstance()
        calendar.time = date  // Asignamos la fecha actual al calendario
        calendar.add(Calendar.DAY_OF_YEAR, days)  // Sumamos los días


        return calendar.time  // Retornamos el nuevo objeto Date
    }

//    /**
//     * Convierte milisegundos a una cadena de fecha formateada.
//     * @param millis Los milisegundos a convertir.
//     * @return La cadena de fecha formateada.
//     */
//    fun convertMillisToDate(millis: Long): String {
//        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        return formatter.format(Date(millis))
//    }
}