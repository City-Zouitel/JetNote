package city.zouitel.reminder.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLayout(
    timePickerState: TimePickerState,
    timePickerDialog: MutableState<Boolean>
) {
    val context = LocalContext.current

    DatePickerDialog(
        onDismissRequest = {
            timePickerDialog.value = false
        },
        confirmButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp, 35.dp),
                onClick = {
                    Toast.makeText(
                        context,
                        "${timePickerState.hour} : ${timePickerState.minute}",
                        Toast.LENGTH_SHORT
                    ).show()
                    timePickerDialog.value = false

                }) {
                Text(text = "Pick", fontSize = 17.sp)
            }

        },
        dismissButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp, 35.dp),
                onClick = {
                    timePickerDialog.value = false
                }) {
                Text(text = "Cansel", fontSize = 17.sp)
            }
        }
    ) {
        TimePicker(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            state = timePickerState,
            layoutType = TimePickerLayoutType.Vertical
        )
    }
}