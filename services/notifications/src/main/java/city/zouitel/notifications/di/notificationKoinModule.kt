package city.zouitel.notifications.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import city.zouitel.notifications.Constants
import city.zouitel.notifications.viewmodel.AlarmManagerScreenModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val notificationKoinModule = module {
    factoryOf(::AlarmManagerScreenModel)

    single {
        NotificationCompat.Builder(androidContext(), Constants.CHANNEL_ID)
            .setDefaults(android.app.Notification.DEFAULT_ALL)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setColor(Color.YELLOW)
            .setLights(Color.WHITE, 300, 100)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    single {
        NotificationManagerCompat.from(androidContext())
    }

    single {
        NotificationChannel(
            Constants.CHANNEL_ID,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }
}