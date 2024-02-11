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
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import city.zouitel.notifications.viewmodel.NotificationVM
import city.zouitel.reminder.viewmodel.ReminderVM
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.KEY_STANDARD
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.CALENDAR_ICON
import city.zouitel.systemDesign.Icons.CLOCK_ICON
import city.zouitel.systemDesign.MaterialColors
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.SoundEffect
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnspecifiedImmutableFlag",
    "UnrememberedMutableState"
)
@Composable
fun RemindingNote(
    dataStoreVM: DataStoreVM = koinViewModel(),
    dialogState: MutableState<Boolean>,
    title: String?,
    message: String?,
    uid: String?,
    remindingValue: MutableState<Long>?
) {
    val ctx = LocalContext.current
//    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)
    val soundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val sound = SoundEffect()
    val getMatColor = MaterialColors().getMaterialColor
    val remindingViewModel = viewModel(ReminderVM::class.java)
    val notifyVM = viewModel(NotificationVM::class.java)

    val datePickerState = rememberDatePickerState()

    val timePickerState = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        title = {
            Row {
                AdaptingRow(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add Reminding", fontSize = 25.sp)
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
                        sound.makeSound(ctx, KEY_CLICK, soundEffect.value)
                        remindingViewModel.getDatePicker(ctx)
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Icon(
                            painterResource(CALENDAR_ICON), null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Pick Date", fontSize = 17.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedIconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        remindingViewModel.getTimePicker(ctx)
                        sound.makeSound(ctx, KEY_CLICK, soundEffect.value)
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Icon(
                            painterResource(CLOCK_ICON),null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Pick Time", fontSize = 17.sp)
                    }
                }
            }
        },
        confirmButton = {
            OutlinedIconButton(
                modifier = Modifier
                    .size(90.dp,35.dp),
                onClick = {
                    runCatching {
                        notifyVM.scheduleNotification(
                            context = ctx,
                            dateTime = remindingViewModel::getTimeReminder.invoke(),
                            title = title,
                            message = message,
                            uid = uid
                        )
                        sound.makeSound(ctx, KEY_STANDARD, soundEffect.value)
                    }.onSuccess {
                        remindingValue?.value = remindingViewModel::getDateTimeReminder.invoke().value.time
                    }

                    dialogState.value = false
                }) {
                Text(text = "Save", fontSize = 17.sp)
            }
        },
        containerColor = getMatColor(SURFACE)
    )
}

