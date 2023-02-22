package com.example.glance

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.currentState
import androidx.glance.text.Text
import com.example.local.model.Entity
import kotlinx.collections.immutable.toImmutableList

class AppWidget: GlanceAppWidget() {

    @Composable
    override fun Content() {
      Text(text = "In Process.")
    }
}