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

}