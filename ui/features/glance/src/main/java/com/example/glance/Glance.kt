package com.example.glance

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.text.Text

class Glance: GlanceAppWidget() {
    @Composable
    override fun Content() {
        Text("JetNote Widget!")
    }
}