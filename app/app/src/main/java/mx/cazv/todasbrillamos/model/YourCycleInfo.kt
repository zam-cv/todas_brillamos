package mx.cazv.todasbrillamos.model

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
