package city.zouitel.notifications.reciver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import city.zouitel.notifications.Cons

class NotificationReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.Q)
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
        val notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notifyChannel = NotificationChannel(
            Cons.CHANNEL_ID,
            Cons.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminder notification"
            enableLights(true)
            lightColor = Color.YELLOW
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            setShowBadge(true)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            setBypassDnd(true)
            setSound(null, null)
            setAllowBubbles(true)
        }

        notifyManager.createNotificationChannel(notifyChannel)

        val notifyBuilder = NotificationCompat.Builder(context, Cons.CHANNEL_ID)
            .setDefaults(android.app.Notification.DEFAULT_ALL)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setColor(Color.YELLOW)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            .setLights(Color.WHITE, 300, 100)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setChannelId(Cons.CHANNEL_ID)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(null, true)
            .setAllowSystemGeneratedContextualActions(true)
            .setGroup("Reminder")
            .setGroupSummary(true)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(intent.getStringExtra("note_title"))
            .setContentText(intent.getStringExtra("note_description"))
            .setAutoCancel(true)
            .build()

        notifyManager.notify(
            intent.getIntExtra("id", 0),
            notifyBuilder
        )

    }
}