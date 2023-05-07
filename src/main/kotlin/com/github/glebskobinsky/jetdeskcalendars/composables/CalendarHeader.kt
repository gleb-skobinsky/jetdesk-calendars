package com.github.glebskobinsky.jetdeskcalendars.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.glebskobinsky.jetdeskcalendars.stringAnnotation.toLocale
import com.github.glebskobinsky.jetdeskcalendars.styles.DateInputDefaults
import com.github.glebskobinsky.jetdeskcalendars.styles.getPointerCursor
import java.time.LocalDateTime

@Composable
@Suppress("FunctionName")
internal fun CalendarHeader(initialDate: MutableState<LocalDateTime>, locale: DateInputDefaults.DateInputLocale) {
    Icon(
        Icons.Outlined.ChevronLeft,
        "Previous month",
        tint = Color.White,
        modifier = Modifier
            .getPointerCursor()
            .clickable {
                initialDate.value = initialDate.value.minusMonths(1L)
            }
    )
    Text(
        "${initialDate.value.dayOfMonth} ${initialDate.value.month.toLocale(locale)} ${initialDate.value.year}",
        color = Color.White,
        modifier = Modifier.width(200.dp),
        textAlign = TextAlign.Center
    )
    Icon(
        Icons.Outlined.ChevronRight,
        "Next month",
        tint = Color.White,
        modifier = Modifier
            .getPointerCursor()
            .clickable {
                initialDate.value = initialDate.value.plusMonths(1L)
            }
    )
}