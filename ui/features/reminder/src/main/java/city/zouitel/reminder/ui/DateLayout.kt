package city.zouitel.reminder.ui

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateLayout(
    selectedDate: MutableState<Long>,
    dateState: DatePickerState,
    dateDialog: MutableState<Boolean>,
    onAction: () -> Unit
) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)

    DatePickerDialog(
        onDismissRequest = {
            onAction.invoke()
            dateDialog.value = false
        },
        confirmButton = {
            OutlinedIconButton(
                modifier = Modifier.size(90.dp, 35.dp),
                onClick = {
                    onAction.invoke()
                    dateDialog.value = false
                    selectedDate.value = dateState.selectedDateMillis!!
                }) {
                Text(text = "Select", fontSize = 16.sp)
            }

        },
        dismissButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp, 35.dp),
                onClick = {
                    onAction.invoke()
                    dateDialog.value = false
                }) {
                Text(text = "Cancel", fontSize = 16.sp)
            }
        }
    ) {

        DatePicker(
            state = dateState,
            title = {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = "Select date"
                )
            },
            headline = {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = formatter.format(Date(dateState.selectedDateMillis!!))
                )
            }
        )
    }
}