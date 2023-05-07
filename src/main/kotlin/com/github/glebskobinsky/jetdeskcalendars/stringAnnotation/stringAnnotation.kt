package com.github.glebskobinsky.jetdeskcalendars.stringAnnotation

import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import java.time.LocalDateTime
import java.time.Month
import java.util.*

internal fun LocalDateTime.displayDate() =
    "${
        this.dayOfMonth.trailZeros()
    }${
        this.month.value.trailZeros()
    }${
        this.year
    }"

internal fun LocalDateTime.displayDateTime() =
    "${
        this.dayOfMonth.trailZeros()
    }${
        this.month?.value.trailZeros()
    }${
        this.year
    }${
        this.hour.trailZeros()
    }${
        this.minute.trailZeros()
    }"

internal fun Int?.trailZeros() = this?.toString()?.padStart(2, '0')

internal fun Month.toLocale(locale: DateInputDefaults.DateInputLocale) = when (locale) {
    DateInputDefaults.DateInputLocale.RU -> when (this) {
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

    DateInputDefaults.DateInputLocale.EN -> this.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
}

fun getApplyText(locale: DateInputDefaults.DateInputLocale): String {
    return when (locale) {
        DateInputDefaults.DateInputLocale.RU -> "Применить"
        DateInputDefaults.DateInputLocale.EN -> "Apply"
    }
}

internal fun String.isValidValue(range: IntRange): Boolean {
    if (isBlank()) {
        return true
    }
    val hourAsInt = trim().toIntOrNull() ?: return false
    return hourAsInt in range
}

internal fun String.isValidHour() = this.isValidValue(0..23)

internal fun String.isValidMinute() = this.isValidValue(0..59)

internal fun String.toTimeInt(): Int {
    return if (isBlank()) {
        0
    } else {
        trim().toInt()
    }
}