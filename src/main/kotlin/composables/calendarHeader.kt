package composables

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
import stringAnnotation.toRu
import styles.getPointerCursor
import java.time.LocalDateTime

@Composable
@Suppress("FunctionName")
internal fun CalendarHeader(initialDate: MutableState<LocalDateTime>) {
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
        "${initialDate.value.dayOfMonth} ${initialDate.value.month.toRu()} ${initialDate.value.year}",
        color = Color.White,
        modifier = Modifier.width(150.dp),
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