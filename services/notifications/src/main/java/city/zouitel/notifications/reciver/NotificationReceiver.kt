package city.zouitel.notifications.reciver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import city.zouitel.notifications.Constants

class NotificationReceiver: BroadcastReceiver() {

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.Q)
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
        val notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val currentChannel = intent.getStringExtra("uid")
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val publicChannel = NotificationChannel(
            currentChannel,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminder notification"
            enableVibration(true)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        notifyManager.createNotificationChannel(publicChannel)

        val notifyBuilder = NotificationCompat.Builder(context, currentChannel ?: Constants.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setSound(sound)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(null, true)
            .setAllowSystemGeneratedContextualActions(true)
            .setGroupSummary(true)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentTitle(intent.getStringExtra("note_title"))
            .setContentText(intent.getStringExtra("note_description"))
            .setGroup(currentChannel)
            .build()

        notifyManager.notify(
            intent.getIntExtra("id", 0),
            notifyBuilder
        )

    }
}