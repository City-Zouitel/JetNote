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
import androidx.compose.runtime.getValue
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
import city.zouitel.logic.events.UiEvent
import city.zouitel.notifications.viewmodel.AlarmManagerScreenModel
import city.zouitel.reminder.layout.DateLayout
import city.zouitel.reminder.layout.TimeLayout
import city.zouitel.reminder.model.Reminder
import city.zouitel.reminder.utils.Constants.SINGLE_DAY
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
import kotlin.random.Random

data class ReminderScreen(
    val uid: String,
    val title: String?,
    val message: String?,
): Screen {
    @Composable
    override fun Content() {
        ReminderSheet(
            dataStoreModel = getScreenModel(),
            reminderModel = getScreenModel(),
            alarmManagerScreenModel = getScreenModel()
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ReminderSheet(
        dataStoreModel: DataStoreScreenModel,
        reminderModel: ReminderScreenModel,
        alarmManagerScreenModel: AlarmManagerScreenModel
    ) {
        val context = LocalContext.current
        val navBottomSheet = LocalBottomSheetNavigator.current

        val id = Random.nextInt()

        val calendar = Calendar.getInstance()

        val isMute by remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
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
                sound.performSoundEffect(context, KEY_CLICK, isMute)
            }
        }

        if (timePickerDialog.value) {
            TimeLayout(
                selectedTime = selectedTime,
                timeState = timeState,
                timePickerDialog = timePickerDialog
            ) {
                sound.performSoundEffect(context, KEY_CLICK, isMute)
            }
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        Navigator(CommonBottomSheet {
            LazyColumn(
                modifier = Modifier.animateContentSize()
            ) {
                item {
                    CommonOptionItem(
                        name = dateFormat.format(Date(selectedDate.longValue)),
                        icon = CALENDAR_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, isMute)
                        datePickerDialog.value = true
                    }
                }
                item {
                    CommonOptionItem(
                        name = "at " + timeFormat.format(Date(selectedTime.longValue)),
                        icon = CLOCK_ICON
                    ) {
                        sound.performSoundEffect(context, KEY_CLICK, isMute)
                        timePickerDialog.value = true
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    CommonOptionItem(
                        onConfirm = {
                            sound.performSoundEffect(context, KEY_STANDARD, isMute)

                            runCatching {
                                alarmManagerScreenModel.initializeAlarm(
                                    uid = uid,
                                    title = title ?: "",
                                    message = message ?: "",
                                    id = id,
                                    atTime = dateTime
                                )

                                reminderModel.sendUiEvent(
                                    UiEvent.Insert(Reminder(id = id.toLong(), uid = uid, atTime = dateTime))
                                )

                                println(id)
                            }

                            navBottomSheet.hide()
                        },
                        onDismiss = {
                            sound.performSoundEffect(context, KEY_CLICK, isMute)
                            dateTime = 0
                            navBottomSheet.hide()
                        }
                    )
                }
            }
        })
    }
}