package com.github.glebskobinsky.jetdeskcalendars.dateTypes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.github.glebskobinsky.jetdeskcalendars.composables.CalendarWindowState
import com.github.glebskobinsky.jetdeskcalendars.composables.getLastDayOfMonth
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.displayDate
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.displayDateTime
import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import java.time.LocalDateTime

sealed class DateInput {
    abstract val format: String

    data class DateRange(
        override val format: String = "ддммггггддммгггг",
        val startDate: MutableState<LocalDateTime?> = mutableStateOf(null),
        val endDate: MutableState<LocalDateTime?> = mutableStateOf(null),
    ) : DateInput()

    data class SingleDate(
        override val format: String = "ддммгггг",
        val date: MutableState<LocalDateTime?> = mutableStateOf(null),
    ) : DateInput()

    data class SingleDateTime(
        override val format: String = "ддммгггг----",
        val dateTime: MutableState<LocalDateTime?> = mutableStateOf(null),
    ) : DateInput()

    fun displayInput(): String {
        return when (this) {
            is DateRange -> if (startDate.value != null && endDate.value != null) "${startDate.value!!.displayDate()}${endDate.value!!.displayDate()}" else format
            is SingleDate -> date.value?.displayDate() ?: format
            is SingleDateTime -> dateTime.value?.displayDateTime() ?: format
        }
    }

    fun clearInput() {
        when (this) {
            is DateRange -> {
                startDate.value = null
                endDate.value = null
            }

            is SingleDate -> {
                date.value = null
            }

            is SingleDateTime -> {
                dateTime.value = null
            }
        }
    }

    fun getResult(): List<LocalDateTime?> {
        return when (this) {
            is DateRange -> {
                listOf(
                    startDate.value,
                    endDate.value
                )
            }

            is SingleDate -> {
                listOf(
                    date.value
                )
            }

            is SingleDateTime -> {
                listOf(
                    dateTime.value
                )
            }
        }
    }

    fun checkIfSelected(date: Int, dateTime: MutableState<LocalDateTime>): Boolean {
        when (this) {
            is DateRange -> {
                val (actualDateStart, actualDateEnd) = getResult()
                if (actualDateStart == null) return false
                if (date > dateTime.value.getLastDayOfMonth()) return false
                if (actualDateEnd == null) {
                    val current = dateTime.value.withDayOfMonth(date).withHour(0).withMinute(0)
                    return actualDateStart == current
                }
                val current = dateTime.value.withDayOfMonth(date)
                return current.isAfter(startDate.value) && current.isBefore(endDate.value)
            }

            is SingleDate -> {
                val (actualDate) = getResult()
                if (actualDate == null) return false
                if (date > dateTime.value.getLastDayOfMonth()) return false
                val current = dateTime.value.withDayOfMonth(date).withHour(0).withMinute(0)
                return actualDate == current
            }

            is SingleDateTime -> {
                val (actualDateTime) = getResult()
                if (actualDateTime == null) return false
                if (date > dateTime.value.getLastDayOfMonth()) return false
                val current = dateTime.value.withDayOfMonth(date)
                return actualDateTime == current
            }
        }
    }

    fun select(dateInt: Int, dateTimeTemp: MutableState<LocalDateTime>, windowState: MutableState<CalendarWindowState>) {
        when (this) {
            is DateRange -> {
                if (startDate.value == null) {
                    startDate.value = dateTimeTemp.value.withDayOfMonth(dateInt).withHour(0).withMinute(0)
                } else {
                    endDate.value = dateTimeTemp.value.withDayOfMonth(dateInt).withHour(0).withMinute(0)
                    val actualDateStart = startDate.value
                    val actualDateEnd = endDate.value
                    if (actualDateStart != null && actualDateEnd != null) {
                        val twoDates = listOf(actualDateStart, actualDateEnd)
                        val spanToReturn = Pair(
                            first = twoDates.min().withHour(0).withMinute(0),
                            second = twoDates.max().withHour(23).withMinute(59)
                        )
                        startDate.value = spanToReturn.first
                        endDate.value = spanToReturn.second
                    }
                }
            }

            is SingleDate -> {
                date.value = dateTimeTemp.value.withDayOfMonth(dateInt).withHour(0).withMinute(0)
            }

            is SingleDateTime -> {
                dateTime.value = dateTimeTemp.value.withDayOfMonth(dateInt)
                windowState.value = CalendarWindowState.TIME
            }
        }
    }

    fun selectHourAndMinute(hour: Int, minute: Int) {
        when (this) {
            is SingleDateTime -> {
                dateTime.value = dateTime.value?.withHour(hour)?.withMinute(minute)
            }
            else -> Unit
        }

    }

    fun parseStringAndSetInput(
        fieldValue: String,
        errorMessage: MutableState<String?>,
        onDateSelected: (List<LocalDateTime?>) -> Unit,
        defaultErrorMessage: String,
    ): Boolean {
        println("Input type: ${this.format}")
        return when (this) {
            is DateRange -> {
                if (fieldValue.length == format.length) {
                    val day1 = fieldValue.substring(0..1).toInt()
                    val month1 = fieldValue.substring(2..3).toInt()
                    val year1 = fieldValue.substring(4..7).toInt()
                    val day2 = fieldValue.substring(8..9).toInt()
                    val month2 = fieldValue.substring(10..11).toInt()
                    val year2 = fieldValue.substring(12..15).toInt()
                    try {
                        val startDateTime = LocalDateTime.of(year1, month1, day1, 0, 0)
                        val endDateTime = LocalDateTime.of(year2, month2, day2, 23, 59)
                        if (startDateTime.isBefore(endDateTime)) {
                            errorMessage.value = null
                            startDate.value = startDateTime
                            endDate.value = endDateTime
                            onDateSelected(getResult())
                        } else {
                            errorMessage.value = defaultErrorMessage
                        }
                    } catch (e: Exception) {
                        errorMessage.value = defaultErrorMessage
                    }
                    true
                } else false

            }

            is SingleDate -> {
                if (fieldValue.length == format.length) {
                    val day1 = fieldValue.substring(0..1).toInt()
                    val month1 = fieldValue.substring(2..3).toInt()
                    val year1 = fieldValue.substring(4..7).toInt()
                    try {
                        val insertedDateTime = LocalDateTime.of(year1, month1, day1, 0, 0)
                        date.value = insertedDateTime
                    } catch (e: Exception) {
                        errorMessage.value = defaultErrorMessage
                    }
                    true
                } else false
            }

            is SingleDateTime -> {
                if (fieldValue.length == format.length) {
                    val day1 = fieldValue.substring(0..1).toInt()
                    val month1 = fieldValue.substring(2..3).toInt()
                    val year1 = fieldValue.substring(4..7).toInt()
                    val hour1 = fieldValue.substring(8..9).toInt()
                    val minute1 = fieldValue.substring(10..11).toInt()
                    try {
                        val insertedDateTime = LocalDateTime.of(year1, month1, day1, hour1, minute1)
                        dateTime.value = insertedDateTime
                    } catch (e: Exception) {
                        print("Failed to use datetime: ${e.message}")
                        errorMessage.value = defaultErrorMessage
                    }
                    true
                } else false
            }
        }

    }
}


fun initializeInput(type: DateTypes, locale: DateInputDefaults.DateInputLocale): DateInput {
    return when (locale) {
        DateInputDefaults.DateInputLocale.EN -> when (type) {
            DateTypes.DATE_RANGE -> DateInput.DateRange("ddmmyyyyddmmyyyy")
            DateTypes.SINGLE_DATE -> DateInput.SingleDate("ddmmyyyy")
            DateTypes.SINGLE_DATETIME -> DateInput.SingleDateTime("ddmmyyyy----")
        }

        DateInputDefaults.DateInputLocale.RU -> when (type) {
            DateTypes.DATE_RANGE -> DateInput.DateRange("ддммггггддммгггг")
            DateTypes.SINGLE_DATE -> DateInput.SingleDate("ддммгггг")
            DateTypes.SINGLE_DATETIME -> DateInput.SingleDateTime("ддммгггг----")
        }
    }
}

enum class DateTypes {
    DATE_RANGE,
    SINGLE_DATE,
    SINGLE_DATETIME
}