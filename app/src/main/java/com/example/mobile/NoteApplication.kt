package com.example.mobile

import android.app.Application
import com.emirhankolver.GlobalExceptionHandler
import com.example.glance.di.glanceModule
import com.karacca.beetle.Beetle
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class NoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //
        GlobalExceptionHandler.initialize(this,NoteActivity::class.java)

        //
        Beetle.configure {
            enableAssignees = true
            enableLabels = true
        }
        Beetle.init(this, "City-Zouitel", "JetNote")

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@NoteApplication)
            modules(
                listOf(
                    glanceModule
                )
            )
        }
    }
}