package city.zouitel.notifications.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import city.zouitel.notifications.Cons
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.notifications.viewmodel.NotificationVM
import org.koin.android.ext.koin.androidContext

val notificationKoinModule = module {
    viewModelOf(::NotificationVM)

    single {
        NotificationCompat.Builder(androidContext(), Cons.CHANNEL_ID)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setColor(Color.YELLOW)
//            .setOngoing(true) to ability cancel the notification or not.
                .setLights(Color.WHITE, 300, 100)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

    single {
        NotificationManagerCompat.from(androidContext())
            .createNotificationChannel(
                NotificationChannel(
                    Cons.CHANNEL_ID,
                    Cons.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
    }
}