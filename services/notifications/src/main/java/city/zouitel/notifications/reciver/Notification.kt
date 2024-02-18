package city.zouitel.notifications.reciver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import city.zouitel.notifications.Cons.DESCRIPTION
import city.zouitel.notifications.Cons.IMAGES
import city.zouitel.notifications.Cons.JPEG
import city.zouitel.notifications.Cons.NOTIFICATION_ID
import city.zouitel.notifications.Cons.TITLE
import city.zouitel.notifications.Cons.UID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import android.graphics.Color
import city.zouitel.notifications.Cons
import org.koin.android.ext.koin.androidContext


class Notification : NotifyBroadcastReceiver(), KoinComponent {

    private val notifyBuilder : NotificationCompat.Builder by inject()
    private val notifyManager : NotificationManagerCompat by inject()
    private val notifyChannel : NotificationChannel by inject()

    override fun onReceive(context: Context, intent: Intent) {

        val path = context.filesDir.path
        val uid = intent.getStringExtra(UID)
        val imagePath = uid?.let { File("$path/$IMAGES", "$it.$JPEG") }
        val bitImg = BitmapFactory.decodeFile(imagePath?.absolutePath)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        runCatching {
            notifyChannel
        }.onSuccess {
            notifyManager.notify(
                NOTIFICATION_ID,
                notifyBuilder
                    .setContentTitle(intent.getStringExtra(TITLE))
                    .setContentText(intent.getStringExtra(DESCRIPTION))
                    .setLargeIcon(bitImg)
                    .build()
            )
        }
    }
}