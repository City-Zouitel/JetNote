package com.example.mobile

import android.content.Intent
import android.service.quicksettings.TileService

class QuickNote: TileService() {

    override fun onClick() {
        super.onClick()
        val intent = Intent(applicationContext, NoteActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        intent.putExtra("quick_note", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)
    }
}