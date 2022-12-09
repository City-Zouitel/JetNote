package com.example.jetnote.vm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.jetnote.Notification
import com.example.jetnote.cons.DESCRIPTION
import com.example.jetnote.cons.TITLE
import com.example.jetnote.cons.UID

class NotificationVM: ViewModel() {

    fun scheduleNotification(
        context: Context,
        dateTime: MutableState<Long?>,
        title: String?,
        message: String?,
        uid:String?
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

        dateTime.value?.let {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                it,
                pendingInt
            )
        }
    }
}