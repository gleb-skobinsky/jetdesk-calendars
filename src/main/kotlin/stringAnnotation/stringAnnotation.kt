package stringAnnotation

import java.time.LocalDateTime
import java.time.Month

internal fun LocalDateTime?.displayDate() =
    "${
        this?.dayOfMonth.trailZeros() ?: "дд"
    }${
        this?.month?.value.trailZeros() ?: "мм"
    }${
        this?.year ?: "гггг"
    }"

internal fun LocalDateTime?.displayDateTime() =
    "${
        this?.dayOfMonth.trailZeros() ?: "дд"
    }${
        this?.month?.value.trailZeros() ?: "мм"
    }${
        this?.year ?: "гггг"
    }${
        this?.hour ?: "--"
    }${
        this?.minute ?: "--"
    }"

internal fun Int?.trailZeros() = this?.toString()?.padStart(2, '0')

const val dateForm = "ддммггггддммгггг"

internal fun Month.toRu() = when (this) {
    Month.JANUARY -> "Января"
    Month.FEBRUARY -> "Февраля"
    Month.MARCH -> "Марта"
    Month.APRIL -> "Апреля"
    Month.MAY -> "Мая"
    Month.JUNE -> "Июня"
    Month.JULY -> "Июля"
    Month.AUGUST -> "Августа"
    Month.SEPTEMBER -> "Сентября"
    Month.OCTOBER -> "Октября"
    Month.NOVEMBER -> "Ноября"
    Month.DECEMBER -> "Декабря"
}