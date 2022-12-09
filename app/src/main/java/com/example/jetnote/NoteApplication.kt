package com.example.jetnote

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
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
        Beetle.init(this, "youndon", "JetNote")
    }
}