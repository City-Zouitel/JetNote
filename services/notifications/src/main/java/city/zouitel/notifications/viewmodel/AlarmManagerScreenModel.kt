package city.zouitel.notifications.viewmodel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.notifications.Constants.ID
import city.zouitel.notifications.Constants.MESSAGE
import city.zouitel.notifications.Constants.TITLE
import city.zouitel.notifications.Constants.UID
import city.zouitel.notifications.reciver.NotificationReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmManagerScreenModel(
    private val context: Context,
    private val updateReminder: ReminderUseCase.Update
): ScreenModel, UiEventHandler<Long> {

    private val intent = Intent(context , NotificationReceiver::class.java)
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val basicPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    @SuppressLint("NewApi")
    fun initializeAlarm(title: String, message: String, uid: String, id: Int, atTime: Long) {
        intent.putExtra(TITLE, title)
        intent.putExtra(MESSAGE, message)
        intent.putExtra(UID, uid)
        intent.putExtra(ID, id)

        scheduleAlarm(atTime, id)
    }

    @SuppressLint("NewApi")
    private fun scheduleAlarm(atTime: Long, id: Int) {
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val isScheduleExactAlarms = alarmManager.canScheduleExactAlarms()

        if (isScheduleExactAlarms) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                atTime,
                pendingIntent ?: basicPendingIntent
            )
        } else {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                atTime,
                pendingIntent ?: basicPendingIntent
            )
        }
    }

    fun cancelAlarm(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    override fun sendUiEvent(event: UiEvent<Long>) {
        when(event) {
            is UiEvent.Update -> performUiEvent { updateReminder(event.data) }
            else -> throw NotImplementedError("UiEvent not implemented: $event")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}