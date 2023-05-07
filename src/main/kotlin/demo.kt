import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
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
import com.github.glebskobinsky.jetdeskcalendars.composables.DatePicker
import com.github.glebskobinsky.jetdeskcalendars.composables.DateRangePicker
import com.github.glebskobinsky.jetdeskcalendars.composables.DateTimePicker

@Composable
@Preview
fun App() {
    MaterialTheme {
        Row(Modifier.padding(32.dp)) {
            Column(
                Modifier.width(500.dp)
            ) {
                Text("Date range picker demo", fontSize = 24.sp)
                DateRangePicker { (startDate, endDate) ->
                    println("$startDate $endDate")
                }
            }
            Spacer(Modifier.width(32.dp))
            Column(
                Modifier.width(500.dp)
            ) {
                Text("Date picker demo", fontSize = 24.sp)
                DatePicker { (date) ->
                    println(date)
                }
            }
            Spacer(Modifier.width(32.dp))
            Column(
                Modifier.width(500.dp)
            ) {
                Text("Datetime picker demo", fontSize = 24.sp)
                DateTimePicker { (dateTime) ->
                    println(dateTime)
                }
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
