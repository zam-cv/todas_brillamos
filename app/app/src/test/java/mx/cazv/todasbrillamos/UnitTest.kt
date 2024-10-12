package mx.cazv.todasbrillamos

import mx.cazv.todasbrillamos.view.components.formatDate
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun formatDate_afternoon_isCorrect() {
        val inputDate = "2023-04-15T14:30:00Z"
        val expectedOutput = "Abril 15, 2023, 02:30 p. m."
        val result = formatDate(inputDate)
        assertEquals(expectedOutput, result)
    }

    @Test
    fun formatDate_morning_isCorrect() {
        val inputDate = "2023-04-15T09:15:00Z"
        val expectedOutput = "Abril 15, 2023, 09:15 a. m."
        val result = formatDate(inputDate)
        assertEquals(expectedOutput, result)
    }

    @Test
    fun formatDate_midnight_isCorrect() {
        val inputDate = "2023-04-15T00:00:00Z"
        val expectedOutput = "Abril 15, 2023, 12:00 a. m."
        val result = formatDate(inputDate)
        assertEquals(expectedOutput, result)
    }

    @Test
    fun formatDate_noon_isCorrect() {
        val inputDate = "2023-04-15T12:00:00Z"
        val expectedOutput = "Abril 15, 2023, 12:00 p. m."
        val result = formatDate(inputDate)
        assertEquals(expectedOutput, result)
    }
}