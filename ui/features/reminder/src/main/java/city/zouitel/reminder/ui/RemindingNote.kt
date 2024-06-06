package city.zouitel.reminder.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.notifications.viewmodel.NotificationScreenModel
import city.zouitel.reminder.utils.Cons.SINGLE_DAY
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.KEY_STANDARD
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons.CALENDAR_ICON
import city.zouitel.systemDesign.CommonIcons.CLOCK_ICON
import city.zouitel.systemDesign.CommonIcons.REFRESH_ICON
import city.zouitel.systemDesign.SoundEffect
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnspecifiedImmutableFlag",
    "UnrememberedMutableState"
)
@Composable
fun RemindingNote(
    dataStoreModel: DataStoreScreenModel,
    notificationModel: NotificationScreenModel,
    title: String?,
    message: String?,
    uid: String?,
    dialogState: (Boolean) -> Unit,
    remindingValue: (Long) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val soundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val sound = SoundEffect()

    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= System.currentTimeMillis() - SINGLE_DAY
            }
        }
    )

    val localDT = LocalDateTime.now()
    val timeState = rememberTimePickerState(
        initialHour = localDT.hour,
        initialMinute = localDT.minute
    )

    val selectedDate = remember { mutableLongStateOf(dateState.selectedDateMillis!!) }
    val selectedTime = remember { mutableLongStateOf(0L) }

    val datePickerDialog = remember { mutableStateOf(false) }
    val timePickerDialog = remember { mutableStateOf(false) }

    var dateTime = selectedTime.longValue + selectedDate.longValue

    if(datePickerDialog.value) {
        DateLayout(selectedDate = selectedDate, dateState = dateState, dateDialog = datePickerDialog) {
            sound.makeSound(context, KEY_CLICK, soundEffect.value)
        }
    }

    if (timePickerDialog.value) {
        TimeLayout(selectedTime = selectedTime, timeState = timeState, timePickerDialog = timePickerDialog) {
            sound.makeSound(context, KEY_CLICK, soundEffect.value)
        }
    }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    AlertDialog(
        onDismissRequest = {
            dialogState.invoke(false)
        },
        title = {
            Row {
                CommonRow(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add Reminder", fontSize = 25.sp)
                }
            }
        },
        text = {
            Column {
                OutlinedIconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        sound.makeSound(context, KEY_CLICK, soundEffect.value)
                        datePickerDialog.value = true
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Icon(
                            painterResource(CALENDAR_ICON), null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = dateFormat.format(Date(selectedDate.longValue)), fontSize = 17.sp)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedIconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        sound.makeSound(context, KEY_CLICK, soundEffect.value)
                        timePickerDialog.value = true
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Icon(
                            painterResource(CLOCK_ICON), null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "at " + timeFormat.format(Date(selectedTime.longValue)), fontSize = 17.sp)
                    }
                }
                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedIconButton(
                        enabled = dateTime != 0L,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            sound.makeSound(context, KEY_CLICK, soundEffect.value)
                            dateTime = 0L
                        }) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        ) {
                            Icon(
                                painterResource(REFRESH_ICON),null,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Reset", fontSize = 17.sp)
                        }
                }
            }
               },
        confirmButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp,35.dp),
                onClick = {
                    sound.makeSound(context, KEY_STANDARD, soundEffect.value)
                    runCatching {
                        notificationModel.scheduleNotification(
                            context = context,
                            dateTime = dateTime,
                            title = title,
                            message = message,
                            uid = uid
                        ) {
                            /**
                             * if true the work manager should be canceled.
                             */
                            dateTime == 0L
                        }
                    }.onSuccess {
                        remindingValue.invoke(dateTime)
                    }
                    dialogState.invoke(false)
                }) {
                Text(text = "Save", fontSize = 16.sp)
            }
        },
        dismissButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp,35.dp),
                onClick = {
                    sound.makeSound(context, KEY_CLICK, soundEffect.value)
                    dialogState.invoke(false)
                }) {
                Text(text = "Cancel", fontSize = 16.sp)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
}