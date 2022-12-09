package com.example.jetnote.di

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jetnote.cons.CHANNEL_ID
import com.example.jetnote.cons.CHANNEL_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationMod {

    @SuppressLint("InvalidColorHexValue")
    @Singleton
    @Provides
    fun notificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(android.app.Notification.DEFAULT_ALL)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setColor(Color.YELLOW)
//            .setOngoing(true) to ability cancel the notification or not.
            .setLights(Color.WHITE, 300, 100)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    @SuppressLint("ServiceCast")
    @Singleton
    @Provides
    fun notificationChannel(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        // Create the NotificationChannel, but only on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}