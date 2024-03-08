package city.zouitel.reminder.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLayout(
    selectedTime: MutableLongState,
    timeState:TimePickerState,
    timePickerDialog: MutableState<Boolean>,
    onAction: () -> Unit
) {

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
                Text(text = "Pick", fontSize = 16.sp)
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
                Text(text = "Cancel", fontSize = 16.sp)
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