package com.example.jetnote

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jetnote.cons.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject
@AndroidEntryPoint
class Notification : HiltBroadcastReceiver() {

    @Inject lateinit var notifyBuilder : NotificationCompat.Builder
    @Inject lateinit var notifyManager : NotificationManagerCompat

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val path = context.filesDir.path
        val uid = intent.getStringExtra(UID)
        val imagePath = uid?.let { File("$path/$IMAGE_FILE", "$it.$JPEG") }
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