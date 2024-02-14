package city.zouitel.reminder.ui

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateLayout(
    datePickerState: DatePickerState,
    datePickerDialog: MutableState<Boolean>
) {
    val context = LocalContext.current

    DatePickerDialog(
        onDismissRequest = {
            datePickerDialog.value = false
        },
        confirmButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp, 35.dp),
                onClick = {
                    Toast.makeText(context, "${datePickerState.selectedDateMillis}", Toast.LENGTH_SHORT).show()
                    datePickerDialog.value = false

                }) {
                Text(text = "Pick", fontSize = 17.sp)
            }

        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text("Title.")
            },
            headline = {
                Text("Headline!")
            }
        )
    }
}