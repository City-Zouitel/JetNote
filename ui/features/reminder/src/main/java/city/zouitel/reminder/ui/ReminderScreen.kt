package city.zouitel.reminder.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.notifications.viewmodel.NotificationScreenModel
import city.zouitel.reminder.utils.Cons.SINGLE_DAY
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.KEY_STANDARD
import city.zouitel.systemDesign.CommonIcons.CALENDAR_ICON
import city.zouitel.systemDesign.CommonIcons.CLOCK_ICON
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ReminderScreen(
    val id: String?,
    val title: String?,
    val message: String?,
    val remindingValue: (Long) -> Unit
): Screen {
    @Composable
    override fun Content() {
        Reminder(
            dataStoreModel = getScreenModel(),
            notificationModel = getScreenModel()
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Reminder(
        dataStoreModel: DataStoreScreenModel,
        notificationModel: NotificationScreenModel,
    ) {
        val context = LocalContext.current
        val navBottomSheet = LocalBottomSheetNavigator.current

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

        if (datePickerDialog.value) {
            DateLayout(
                selectedDate = selectedDate,
                dateState = dateState,
                dateDialog = datePickerDialog
            ) {
                sound.makeSound(context, KEY_CLICK, soundEffect.value)
            }
        }

        if (timePickerDialog.value) {
            TimeLayout(
                selectedTime = selectedTime,
                timeState = timeState,
                timePickerDialog = timePickerDialog
            ) {
                sound.makeSound(context, KEY_CLICK, soundEffect.value)
            }
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        Navigator(CommonBottomSheet({
            LazyColumn(
                modifier = Modifier.animateContentSize()
            ) {
                item {
                    CommonOptionItem(
                        name = dateFormat.format(Date(selectedDate.longValue)),
                        icon = CALENDAR_ICON
                    ) {
                        sound.makeSound(context, KEY_CLICK, soundEffect.value)
                        datePickerDialog.value = true
                    }
                }
                item {
                    CommonOptionItem(
                        name = "at " + timeFormat.format(Date(selectedTime.longValue)),
                        icon = CLOCK_ICON
                    ) {
                        sound.makeSound(context, KEY_CLICK, soundEffect.value)
                        timePickerDialog.value = true
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    CommonOptionItem(
                        onConfirm = {
                            sound.makeSound(context, KEY_STANDARD, soundEffect.value)
                            runCatching {
                                notificationModel.scheduleNotification(
                                    context = context,
                                    dateTime = dateTime,
                                    title = title,
                                    message = message,
                                    uid = id
                                ) {
                                    /**
                                     * if true the work manager should be canceled.
                                     */
                                    dateTime == 0L
                                }
                            }.onSuccess {
                                remindingValue.invoke(dateTime)
                            }
                            navBottomSheet.hide()
                        },
                        onDismiss = {
                            sound.makeSound(context, KEY_CLICK, soundEffect.value)
                            dateTime = 0
                            navBottomSheet.hide()
                        }
                    )
                }
            }
        }))
    }
}