package com.example.jetnote

import android.app.Application
import com.emirhankolver.GlobalExceptionHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //
        GlobalExceptionHandler.initialize(this,NoteActivity::class.java)
    }
}