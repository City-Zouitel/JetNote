package city.zouitel.notifications.reciver

import android.Manifest
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
import java.io.File

//@AndroidEntryPoint
class Notification : NotifyBroadcastReceiver() {

    /*@Inject*/ lateinit var notifyBuilder : NotificationCompat.Builder
    /*@Inject*/ lateinit var notifyManager : NotificationManagerCompat

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

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