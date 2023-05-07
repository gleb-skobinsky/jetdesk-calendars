package com.github.glebskobinsky.jetdeskcalendars.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.glebskobinsky.jetdeskcalendars.dateTypes.DateInput
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.getApplyText
import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import com.github.glebskobinsky.jetdeskcalendars.styles.getPointerCursor
import java.time.LocalDateTime

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Suppress("FunctionName")
fun Calendar(
    dateTime: MutableState<LocalDateTime>,
    dateHolder: MutableState<DateInput>,
    groundFieldFocusRequester: FocusRequester,
    popupOpened: MutableState<Boolean>,
    onDateSelected: (List<LocalDateTime?>) -> Unit,
    errorMessage: MutableState<String?>,
    background: Color,
    locale: DateInputDefaults.DateInputLocale,
) {
    val windowState = remember { mutableStateOf(CalendarWindowState.CALENDAR) }
    when (windowState.value) {
        CalendarWindowState.CALENDAR -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CalendarHeader(dateTime, locale)
            }
            val dates = dateTime.value.getMonthGrid()
            Column(
                Modifier.fillMaxWidth()
            ) {
                dates.forEach { week ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        week.forEach { date ->
                            var hovered by remember { mutableStateOf(false) }
                            val selected by derivedStateOf {
                                date?.let {
                                    dateHolder.value.checkIfSelected(date, dateTime)
                                } ?: false
                            }
                            Text(
                                text = date?.toString() ?: "",
                                fontSize = 12.sp,
                                color = if (selected) Color.Black else Color.White,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(2.dp, if (hovered) Color.White else Color.Transparent, RoundedCornerShape(20.dp))
                                    .onPointerEvent(eventType = PointerEventType.Enter) {
                                        date?.let {
                                            hovered = true
                                        }
                                    }
                                    .onPointerEvent(eventType = PointerEventType.Exit) {
                                        date?.let {
                                            hovered = false
                                        }
                                    }
                                    .getPointerCursor()
                                    .clickable(
                                        enabled = date != null
                                    ) {
                                        dateHolder.value.select(date!!, dateTime, windowState)
                                    }
                                    .background(if (selected) Color.White else Color.Transparent)
                                    .padding(vertical = 12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        CalendarWindowState.TIME -> {
            TimeInput(
                dateHolder = dateHolder,
                groundFieldFocusRequester = groundFieldFocusRequester,
                popupOpened = popupOpened,
                windowState = windowState,
                onDateSelected = onDateSelected
            )
        }
    }

    if (dateHolder.value !is DateInput.SingleDateTime) {
        Button(
            onClick = {
                onDateSelected(dateHolder.value.getResult())
                groundFieldFocusRequester.requestFocus()
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



