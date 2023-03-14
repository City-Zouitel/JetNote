package com.example.glance.data

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.example.glance.presentation.NoteGlanceWidget

class GlanceReceiver:GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = NoteGlanceWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        NoteWorker.enq(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        NoteWorker.cancel(context)
    }
}