package com.example.mobile

import android.app.Application
import com.emirhankolver.GlobalExceptionHandler
import com.karacca.beetle.Beetle
import dagger.hilt.android.HiltAndroidApp

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
    }
}