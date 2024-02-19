package city.zouitel.reminder.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLayout(
    selectedTime: MutableLongState,
    timePickerDialog: MutableState<Boolean>,
    onAction: () -> Unit
) {
    val localDT = LocalDateTime.now()

    val timeState = rememberTimePickerState(
        initialHour = localDT.hour,
        initialMinute = localDT.minute
    )

    DatePickerDialog(
        onDismissRequest = {
            timePickerDialog.value = false
        },
        confirmButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp, 35.dp),
                onClick = {
                    onAction.invoke()
                    timePickerDialog.value = false
                    selectedTime.longValue = (((timeState.hour - 1) * 60 + timeState.minute) * 60 * 1000L)
                }) {
                Text(text = "Pick", fontSize = 17.sp)
            }
        },
        dismissButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp, 35.dp),
                onClick = {
                    onAction.invoke()
                    timePickerDialog.value = false
                }) {
                Text(text = "Cansel", fontSize = 17.sp)
            }
        }
    ) {
        TimePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            state = timeState,
            layoutType = TimePickerLayoutType.Vertical
        )
    }
}