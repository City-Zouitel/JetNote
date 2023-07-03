package com.example.mobile

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.emirhankolver.GlobalExceptionHandler
import com.karacca.beetle.Beetle
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NoteApplication: Application()/*, Configuration.Provider*/ {

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

    }


    /**
     * Work Manager Initializer.
     */
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()
//    }
}