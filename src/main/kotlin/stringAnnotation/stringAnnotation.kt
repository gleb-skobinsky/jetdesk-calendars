package stringAnnotation

import styles.DateInoutDefaults
import java.time.LocalDateTime
import java.time.Month
import java.util.*

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

internal fun Month.toLocale(locale: DateInoutDefaults.DateInputLocale) = when (locale) {
    DateInoutDefaults.DateInputLocale.RU -> when (this) {
        Month.JANUARY -> "января"
        Month.FEBRUARY -> "февраля"
        Month.MARCH -> "марта"
        Month.APRIL -> "апреля"
        Month.MAY -> "мая"
        Month.JUNE -> "июня"
        Month.JULY -> "июля"
        Month.AUGUST -> "августа"
        Month.SEPTEMBER -> "сентября"
        Month.OCTOBER -> "октября"
        Month.NOVEMBER -> "ноября"
        Month.DECEMBER -> "декабря"
    }

    DateInoutDefaults.DateInputLocale.EN -> this.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
}

