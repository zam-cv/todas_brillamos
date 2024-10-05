package mx.cazv.todasbrillamos.model

/**
 * Clase de datos que representa la información del ciclo menstrual.
 *
 * @author Min Che Kim
 *
 * @property date La fecha del ciclo en formato de tiempo Unix.
 * @property period La duración del periodo en días.
 * @property cycle La duración del ciclo en días.
 * @property nextPeriodStartDate La fecha de inicio del próximo periodo en formato de tiempo Unix.
 * @property nextPeriodEndDate La fecha de finalización del próximo periodo en formato de tiempo Unix.
 * @property ovulationDate La fecha de ovulación en formato de tiempo Unix.
 * @property fertileDayStart La fecha de inicio del periodo fértil en formato de tiempo Unix.
 * @property fertileDayEnd La fecha de finalización del periodo fértil en formato de tiempo Unix.
 */
data class YourCycleInfo(
    val date: Long = 0L,
    val period: String = "0",
    val cycle: String = "0",

    val nextPeriodStartDate: Long = 0L,
    val nextPeriodEndDate: Long = 0L,
    val ovulationDate: Long = 0L,
    val fertileDayStart: Long = 0L,
    val fertileDayEnd: Long = 0L
)
