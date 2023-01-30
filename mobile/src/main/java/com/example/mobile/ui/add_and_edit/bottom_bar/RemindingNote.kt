package com.example.mobile.ui.add_and_edit.bottom_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastore.DataStore
import com.example.mobile.cons.KEY_CLICK
import com.example.mobile.cons.KEY_STANDARD
import com.example.mobile.cons.SURFACE
import com.example.mobile.fp.getMaterialColor
import com.example.mobile.icons.CALENDAR_ICON
import com.example.mobile.icons.CLOCK_ICON
import com.example.mobile.ui.AdaptingRow
import com.example.mobile.ui.settings_screen.makeSound
import com.example.mobile.vm.NotificationVM
import com.example.mobile.vm.ReminderVM

@SuppressLint(
    "UnspecifiedImmutableFlag",
    "UnrememberedMutableState"
)
@Composable
fun RemindingNote(
    dialogState: MutableState<Boolean>,
    title: String?,
    message: String?,
    uid: String?,
    remindingValue: MutableState<Long>?
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

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
                            .makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)
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
                            .makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)
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
                        ).makeSound(ctx, KEY_STANDARD, thereIsSoundEffect.value)
                    }.onSuccess {
                        remindingValue?.value = remindingViewModel::getDateTimeReminder.invoke().value.time
                    }

                    dialogState.value = false
                }) {
                Text(text = "Save", fontSize = 17.sp)
            }
        },
        containerColor = getMaterialColor(SURFACE)
    )
}

