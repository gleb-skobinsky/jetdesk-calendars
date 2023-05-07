package com.github.glebskobinsky.jetdeskcalendars.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.glebskobinsky.jetdeskcalendars.dateTypes.DateInput
import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import java.time.LocalDateTime

@Composable
@Suppress("FunctionName")
fun CalendarSpanPopup(
    dateTime: MutableState<LocalDateTime>,
    dateHolder: MutableState<DateInput>,
    groundFieldFocusRequester: FocusRequester,
    popupOpened: MutableState<Boolean>,
    onDateSelected: (List<LocalDateTime?>) -> Unit,
    errorMessage: MutableState<String?>,
    background: Color,
    locale: DateInputDefaults.DateInputLocale
) {
    Surface(
        elevation = 12.dp,
        color = Color.Transparent,
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
    ) {
        Column(
            modifier = Modifier
                .background(background, RoundedCornerShape(5.dp))
                .width(304.dp)
                .padding(bottom = 12.dp, top = 6.dp, start = 12.dp, end = 12.dp)
        ) {
            Calendar(
                dateTime = dateTime,
                dateHolder = dateHolder,
                groundFieldFocusRequester = groundFieldFocusRequester,
                popupOpened = popupOpened,
                onDateSelected = onDateSelected,
                errorMessage = errorMessage,
                background = background,
                locale = locale
            )
        }
    }
}
