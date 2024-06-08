package city.zouitel.notifications.reciver

import android.Manifest
import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import city.zouitel.notifications.Cons.DESCRIPTION
import city.zouitel.notifications.Cons.IMG_DIR
import city.zouitel.notifications.Cons.JPEG
import city.zouitel.notifications.Cons.NOTIFICATION_ID
import city.zouitel.notifications.Cons.TITLE
import city.zouitel.notifications.Cons.UID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File


class Notification : NotifyBroadcastReceiver(), KoinComponent {

    private val notifyBuilder : NotificationCompat.Builder by inject()
    private val notifyManager : NotificationManagerCompat by inject()
    private val notifyChannel : NotificationChannel by inject()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val path = context.filesDir.path
        val uid = intent.getStringExtra(UID)
        val imagePath = uid?.let { File("$path/$IMG_DIR", "$it.$JPEG") }
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