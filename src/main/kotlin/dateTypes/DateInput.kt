package dateTypes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import composables.getLastDayOfMonth
import stringAnnotation.displayDate
import stringAnnotation.displayDateTime
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
            is DateRange -> "${startDate.value.displayDate()}${endDate.value.displayDate()}"
            is SingleDate -> date.value.displayDate()
            is SingleDateTime -> dateTime.value.displayDateTime()
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
        return when (this) {
            is DateRange -> {
                val (actualDateStart, actualDateEnd) = getResult()
                if (actualDateStart != null && actualDateEnd != null && date in 1..dateTime.value.getLastDayOfMonth()) {
                    val current = dateTime.value.withDayOfMonth(date)
                    current.isAfter(startDate.value) && current.isBefore(endDate.value)
                } else false
            }

            is SingleDate -> false
            is SingleDateTime -> false
        }
    }

    fun select(dateInt: Int, dateTime: MutableState<LocalDateTime>) {
        when (this) {
            is DateRange -> {
                if (startDate.value == null) {
                    startDate.value = dateTime.value.withDayOfMonth(dateInt).withHour(0).withMinute(0)
                } else {
                    endDate.value = dateTime.value.withDayOfMonth(dateInt).withHour(0).withMinute(0)
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
                date.value = dateTime.value.withDayOfMonth(dateInt).withHour(0).withMinute(0)
            }

            is SingleDateTime -> {
                dateTime.value = dateTime.value.withDayOfMonth(dateInt)
            }
        }
    }

    fun parseStringAndSetInput(
        fieldValue: String,
        errorMessage: MutableState<String?>,
        onDateSelected: (List<LocalDateTime?>) -> Unit,
        defaultErrorMessage: String,
    ): Boolean {
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

            is SingleDate -> TODO()
            is SingleDateTime -> TODO()
        }

    }
}


fun initializeInput(type: DateTypes): DateInput {
    return when (type) {
        DateTypes.DATE_RANGE -> DateInput.DateRange()
        DateTypes.SINGLE_DATE -> DateInput.SingleDate()
        DateTypes.SINGLE_DATETIME -> DateInput.SingleDateTime()
    }
}

enum class DateTypes {
    DATE_RANGE,
    SINGLE_DATE,
    SINGLE_DATETIME
}