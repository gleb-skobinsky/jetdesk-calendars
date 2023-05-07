package com.github.glebskobinsky.jetdeskcalendars.styles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.glebskobinsky.jetdeskcalendars.dateTypes.DateInput
import java.awt.Cursor

internal fun Modifier.getBorderModifier(
    focused: Boolean,
    dateHolder: DateInput,
    colors: DateInputDefaults.DateInputColors,
    borders: DateInputDefaults.DateInputBorders,
) = when (dateHolder) {
    is DateInput.DateRange -> {
        if (focused) {
            this.background(colors.focusedColor).focusedBorder(borders.focusedBorder)
        } else {
            if (dateHolder.startDate.value != null && dateHolder.endDate.value != null) {
                this.background(colors.withValueColor).notEmptyFieldBorder(borders.withValueBorder)
            } else {
                this.background(colors.emptyColor).simpleBorder(borders.simpleBorder)
            }
        }
    }

    is DateInput.SingleDate -> {
        if (focused) {
            this.background(colors.focusedColor).focusedBorder(borders.focusedBorder)
        } else {
            if (dateHolder.date.value != null) {
                this.background(colors.withValueColor).notEmptyFieldBorder(borders.withValueBorder)
            } else {
                this.background(colors.emptyColor).simpleBorder(borders.simpleBorder)
            }
        }
    }

    is DateInput.SingleDateTime -> {
        if (focused) {
            this.background(colors.focusedColor).focusedBorder(borders.focusedBorder)
        } else {
            if (dateHolder.dateTime.value != null) {
                this.background(colors.withValueColor).notEmptyFieldBorder(borders.withValueBorder)
            } else {
                this.background(colors.emptyColor).simpleBorder(borders.simpleBorder)
            }
        }
    }
}

fun Modifier.getPointerCursor() = this.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)), true)

fun Modifier.focusedBorder(border: BorderSpecs) = this.border(
    width = border.width,
    brush = SolidColor(border.color),
    shape = RectangleShape
)

fun Modifier.simpleBorder(border: BorderSpecs) = this.border(
    width = border.width,
    brush = SolidColor(border.color),
    shape = RectangleShape
)

fun Modifier.notEmptyFieldBorder(border: BorderSpecs) = this.border(
    width = border.width,
    brush = SolidColor(border.color),
    shape = RectangleShape
)

class DateInputDefaults {
    data class DateInputColors(
        val emptyColor: Color = inactiveFilterColor,
        val focusedColor: Color = background,
        val withValueColor: Color = background,
    )

    data class DateInputBorders(
        val focusedBorder: BorderSpecs = BorderSpecs(2.dp, selected),
        val simpleBorder: BorderSpecs = BorderSpecs(1.dp, enabled),
        val withValueBorder: BorderSpecs = BorderSpecs(2.dp, enabled),
    )

    enum class DateInputLocale {
        RU,
        EN
    }
}

fun getDefaultErrorMessage(locale: DateInputDefaults.DateInputLocale) = when (locale) {
    DateInputDefaults.DateInputLocale.EN -> "Wrong dates"
    DateInputDefaults.DateInputLocale.RU -> "Неверные даты"
}

data class BorderSpecs(
    val width: Dp,
    val color: Color,
)