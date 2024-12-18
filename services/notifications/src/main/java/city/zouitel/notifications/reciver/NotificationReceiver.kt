package city.zouitel.notifications.reciver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import city.zouitel.logic.events.UiEvent
import city.zouitel.notifications.Constants
import city.zouitel.notifications.Constants.ID
import city.zouitel.notifications.Constants.MESSAGE
import city.zouitel.notifications.Constants.TITLE
import city.zouitel.notifications.Constants.UID
import city.zouitel.notifications.viewmodel.AlarmManagerScreenModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationReceiver: BroadcastReceiver(), KoinComponent {

    private val alarmModel by inject<AlarmManagerScreenModel>()

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.Q)
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
        val notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val currentChannel = intent.getStringExtra(UID)

        val publicChannel = NotificationChannel(
            currentChannel,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminder notification"
            enableVibration(true)
            enableLights(true)
            vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            setShowBadge(true)
        }

        notifyManager.createNotificationChannel(publicChannel)

        val notifyBuilder = NotificationCompat.Builder(context, currentChannel ?: Constants.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setFullScreenIntent(null, true)
            .setAllowSystemGeneratedContextualActions(true)
            .setGroupSummary(true)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentTitle(intent.getStringExtra(TITLE))
            .setContentText(intent.getStringExtra(MESSAGE))
            .build()

        notifyManager.notify(
            intent.getIntExtra(ID, 0),
            notifyBuilder
        )

        alarmModel.sendUiEvent(UiEvent.Update(intent.getIntExtra(ID, 0).toLong()))
    }
}