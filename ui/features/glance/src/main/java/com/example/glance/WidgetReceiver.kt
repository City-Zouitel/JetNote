package com.example.glance

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WidgetReceiver:GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = AppWidget()
}