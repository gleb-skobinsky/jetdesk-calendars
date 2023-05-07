package com.github.glebskobinsky.jetdeskcalendars.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.glebskobinsky.jetdeskcalendars.dateTypes.DateInput
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.getApplyText
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.isValidHour
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.isValidMinute
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.toTimeInt
import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import com.github.glebskobinsky.jetdeskcalendars.styles.getPointerCursor
import java.time.LocalDateTime

@Composable
@Suppress("FunctionName")
internal fun TimeInput(
    dateHolder: MutableState<DateInput>,
    popupOpened: MutableState<Boolean>,
    groundFieldFocusRequester: FocusRequester,
    windowState: MutableState<CalendarWindowState>,
    errorMessage: MutableState<String?>,
    locale: DateInputDefaults.DateInputLocale,
    onDateSelected: (List<LocalDateTime?>) -> Unit,
    background: Color
) {
    val hourField = remember {
        mutableStateOf(
            TextFieldValue(" ")
        )
    }
    val minuteField = remember {
        mutableStateOf(
            TextFieldValue(" ")
        )
    }
    val focusRequesterHours = remember { FocusRequester() }
    val focusRequesterMinutes = remember { FocusRequester() }
    val currentlyFocusedField = remember { mutableStateOf(FocusedField.HOUR) }
    rememberCoroutineScope()
    val commonModifier = Modifier
        /*
        .onKeyEvent {
            if (it.key == Key.Enter && it.type == KeyEventType.KeyDown) {
                dateHolder.value.selectHourAndMinute(hourField.value.text.toTimeInt(), minuteField.value.text.toTimeInt())
                onDateSelected(dateHolder.value.getResult())
                groundFieldFocusRequester.requestFocus()
                errorMessage.value = null
                windowState.value = CalendarWindowState.CALENDAR
                popupOpened.value = false
                false
            } else false
        }
         */
    Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minWidth = 100.dp, 180.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = hourField.value,
            onValueChange = { value ->
                if (value.text.isValidHour()) {
                    hourField.value = value
                    val trimmed = hourField.value.text.trim().length
                    if (trimmed == 2) {
                        currentlyFocusedField.value = FocusedField.MINUTE
                    }
                }
            },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(Color.White),
            modifier = commonModifier
                .width(50.dp)
                .focusRequester(focusRequesterHours)
        )
        Text(":", color = Color.White, fontSize = 20.sp, textAlign = TextAlign.Center)
        BasicTextField(
            value = minuteField.value,
            onValueChange = { value ->
                if (value.text.isValidMinute()) {
                    minuteField.value = value
                }
            },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(Color.White),
            modifier = commonModifier
                .width(50.dp)
                .focusRequester(focusRequesterMinutes)
        )
        LaunchedEffect(currentlyFocusedField.value) {
            when (currentlyFocusedField.value) {
                FocusedField.HOUR -> {
                    if (hourField.value.selection == TextRange(0, 0)) {
                        hourField.value = hourField.value.copy(selection = TextRange(0))
                    }
                    focusRequesterHours.requestFocus()
                }

                FocusedField.MINUTE -> {
                    if (minuteField.value.selection == TextRange(0, 0)) {
                        minuteField.value = minuteField.value.copy(selection = TextRange(0))
                    }
                    focusRequesterMinutes.requestFocus()
                }
            }
        }
    }
        Button(
            onClick = {
                dateHolder.value.selectHourAndMinute(hourField.value.text.toTimeInt(), minuteField.value.text.toTimeInt())
                onDateSelected(dateHolder.value.getResult())
                groundFieldFocusRequester.requestFocus()
                windowState.value = CalendarWindowState.CALENDAR
                errorMessage.value = null
                popupOpened.value = false
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = background),
            modifier = Modifier
                .fillMaxWidth()
                .getPointerCursor()
        ) {
            Text(
                getApplyText(locale),
                color = Color.White,
                modifier = Modifier
            )
        }
    }
}

internal enum class FocusedField {
    HOUR,
    MINUTE
}