package com.github.glebskobinsky.jetdeskcalendars.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.glebskobinsky.jetdeskcalendars.dateTypes.DateTypes
import com.github.glebskobinsky.jetdeskcalendars.dateTypes.initializeInput
import com.github.glebskobinsky.jetdeskcalendars.inputMask.DateMaskTransformation
import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import com.github.glebskobinsky.jetdeskcalendars.styles.enabled
import com.github.glebskobinsky.jetdeskcalendars.styles.getDefaultErrorMessage
import com.github.glebskobinsky.jetdeskcalendars.styles.selected
import java.time.LocalDateTime

@Composable
@Suppress("FunctionName")
fun DatePicker(
    modifier: Modifier = Modifier,
    colors: DateInputDefaults.DateInputColors = DateInputDefaults.DateInputColors(),
    borders: DateInputDefaults.DateInputBorders = DateInputDefaults.DateInputBorders(),
    locale: DateInputDefaults.DateInputLocale = DateInputDefaults.DateInputLocale.EN,
    errorMessage: String = getDefaultErrorMessage(locale),
    iconColor: Color = enabled,
    calendarBackground: Color = selected,
    onDateSelected: (List<LocalDateTime?>) -> Unit,
) {
    val dateHolder = remember { mutableStateOf(initializeInput(DateTypes.SINGLE_DATE, locale)) }
    GenericCalendarInput(
        dateHolder = dateHolder,
        modifier = modifier,
        colors = colors,
        borders = borders,
        iconColor = iconColor,
        onDateSelected = onDateSelected,
        errorMessage = errorMessage,
        calendarBackground = calendarBackground,
        locale = locale,
        mask = DateMaskTransformation()
    )
}