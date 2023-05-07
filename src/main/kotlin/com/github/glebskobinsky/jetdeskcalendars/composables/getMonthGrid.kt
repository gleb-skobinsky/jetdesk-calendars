package com.github.glebskobinsky.jetdeskcalendars.composables

import java.time.LocalDateTime

internal fun LocalDateTime.getMonthGrid(): List<List<Int?>> {
    val year = year
    val month = month
    val firstDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 - 1
    val daysInMonth = getLastDayOfMonth()
    val grid = mutableListOf<List<Int?>>()
    val week = mutableListOf<Int?>()
    var day = 1
    // Add nulls for days before the first day of the month
    for (i in 1..firstDayOfWeek) {
        week.add(null)
    }
    // Add days of the current month
    while (day <= daysInMonth) {
        week.add(day)
        if (week.size == 7) {
            grid.add(week.toList())
            week.clear()
        }
        day++
    }
    // Add nulls for days after the last day of the month
    while (week.size < 7) {
        week.add(null)
    }
    grid.add(week.toList())
    return grid.toList()
}

fun LocalDateTime.getLastDayOfMonth(): Int = withDayOfMonth(month.length(toLocalDate().isLeapYear)).dayOfMonth
