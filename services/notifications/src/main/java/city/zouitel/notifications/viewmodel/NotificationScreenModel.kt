package city.zouitel.notifications.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.notifications.Cons.DESCRIPTION
import city.zouitel.notifications.Cons.TITLE
import city.zouitel.notifications.Cons.UID
import city.zouitel.notifications.reciver.Notification

class NotificationScreenModel: ScreenModel {
    fun scheduleNotification(
        context: Context,
        dateTime: Long,
        title: String?,
        message: String?,
        uid: String?,
        onReset: () -> Boolean
    ) {
        val intent = Intent(context.applicationContext, Notification::class.java)
        intent.putExtra(TITLE, title)
        intent.putExtra(DESCRIPTION, message)
        intent.putExtra(UID, uid)

        val pendingInt = PendingIntent.getBroadcast(
            context.applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        dateTime.let {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                it,
                pendingInt
            )
        }

        if (onReset.invoke()) alarmManager.cancel(pendingInt)
    }
}