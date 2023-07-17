package com.example.reminder

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.AdaptingRow
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Cons.KEY_STANDARD
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons.CALENDAR_ICON
import com.example.common_ui.Icons.CLOCK_ICON
import com.example.common_ui.MaterialColors
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.common_ui.SoundEffect
import com.example.notification.viewmodel.NotificationVM

@SuppressLint(
    "UnspecifiedImmutableFlag",
    "UnrememberedMutableState"
)
@Composable
fun RemindingNote(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    title: String?,
    message: String?,
    uid: String?,
    remindingValue: MutableState<Long>?
) {
    val ctx = LocalContext.current
    val soundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val sound = SoundEffect()
    val getMatColor = MaterialColors().getMaterialColor
    val remindingViewModel = viewModel(ReminderVM::class.java)
    val notifyVM = viewModel(NotificationVM::class.java)

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
                        remindingViewModel.getDatePicker(ctx)
                            sound.makeSound(ctx, KEY_CLICK, soundEffect.value)
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
                        sound.makeSound(ctx, KEY_CLICK, soundEffect.value)
                        remindingViewModel.getTimePicker(ctx)
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
                        sound.makeSound(ctx, KEY_STANDARD, soundEffect.value)
                        notifyVM.scheduleNotification(
                            context = ctx,
                            dateTime = remindingViewModel::getTimeReminder.invoke(),
                            title = title,
                            message = message,
                            uid = uid
                        )
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

