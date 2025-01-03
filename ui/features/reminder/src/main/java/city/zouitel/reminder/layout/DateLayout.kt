package city.zouitel.reminder.layout

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
import city.zouitel.systemDesign.CommonConstants.TITLE_SIZE
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
                Text(text = "Confirm", fontSize = 16.sp)
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
                Text(text = "Dismiss", fontSize = 16.sp)
            }
        }
    ) {
        DatePicker(
            state = dateState,
            title = {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = "Select date",
                    fontSize = TITLE_SIZE.sp
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