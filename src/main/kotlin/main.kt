import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import composables.DatePicker
import composables.DateSpanPicker

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            Modifier.width(600.dp)
        ) {
            Text("Date range picker demo", fontSize = 24.sp)
            DateSpanPicker { (startDate, endDate) ->
                println("$startDate $endDate")
            }
            Spacer(Modifier.height(20.dp))
            Text("Date picker demo", fontSize = 24.sp)
            DatePicker { (date) ->
                println(date)
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(placement = WindowPlacement.Maximized)
    ) {
        App()
    }
}
