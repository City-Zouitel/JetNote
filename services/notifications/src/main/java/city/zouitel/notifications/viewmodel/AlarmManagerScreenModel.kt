package city.zouitel.notifications.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.notifications.reciver.NotificationReceiver

class AlarmManagerScreenModel(private val context: Context): ScreenModel {
    val intent = Intent(context , NotificationReceiver::class.java)
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val basicPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    fun initializeAlarm(title: String, message: String, uid: String, id: Int, atTime: Long) {
        intent.putExtra("note_title", title)
        intent.putExtra("note_description", message)
        intent.putExtra("uid", uid)
        intent.putExtra("id", id)

        scheduleAlarm(atTime, id)
    }

    private fun scheduleAlarm(atTime: Long, id: Int) {
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            atTime,
            pendingIntent ?: basicPendingIntent
        )
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
}