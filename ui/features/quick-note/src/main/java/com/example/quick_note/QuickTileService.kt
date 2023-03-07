package com.example.quick_note

import android.content.Intent
import android.service.quicksettings.TileService

class QuickTileService: TileService() {

    override fun onClick() {
        super.onClick()
        val intent = Intent(applicationContext, QuickActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        intent.putExtra("quick_note", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)
    }
}