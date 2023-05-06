package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dateTypes.DateTypes
import dateTypes.initializeInput
import inputMask.DateMaskTransformation
import styles.DateInoutDefaults
import styles.enabled
import styles.getDefaultErrorMessage
import styles.selected
import java.time.LocalDateTime

@Composable
@Suppress("FunctionName")
fun DatePicker(
    modifier: Modifier = Modifier,
    colors: DateInoutDefaults.DateInputColors = DateInoutDefaults.DateInputColors(),
    borders: DateInoutDefaults.DateInputBorders = DateInoutDefaults.DateInputBorders(),
    locale: DateInoutDefaults.DateInputLocale = DateInoutDefaults.DateInputLocale.EN,
    errorMessage: String = getDefaultErrorMessage(locale),
    iconColor: Color = enabled,
    calendarBackground: Color = selected,
    onDateSelected: (List<LocalDateTime?>) -> Unit,
) {
    val dateHolder = remember { mutableStateOf(initializeInput(DateTypes.SINGLE_DATE)) }
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