package com.example.mobile

import android.app.Application
import androidx.startup.AppInitializer
import com.emirhankolver.GlobalExceptionHandler
import com.karacca.beetle.Beetle
import dagger.hilt.android.HiltAndroidApp
import city.zouitel.init.WorkManagerInitializer

@HiltAndroidApp
class NoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Global Exception Handler.
         */
        GlobalExceptionHandler.initialize(this, NoteActivity::class.java)

        /**
         *
         */
        Beetle.configure {
            enableAssignees = true
            enableLabels = true
        }
        Beetle.init(this, "City-Zouitel", "JetNote")

        /**
         * Work Manager Initializer.
         */
        AppInitializer.getInstance(this).initializeComponent(WorkManagerInitializer::class.java)
    }

}