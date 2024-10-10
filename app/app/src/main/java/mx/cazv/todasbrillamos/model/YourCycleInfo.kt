package mx.cazv.todasbrillamos.model

import java.util.Date

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

    val nextPeriodStartDate: Date = Date(),
    val nextPeriodEndDate: Date = Date(),
    val ovulationDate: Date = Date(),
    val fertileDayStart: Date = Date(),
    val fertileDayEnd: Date = Date(),

    val threeCycles: List<YourCycleInfo> = emptyList()
)
