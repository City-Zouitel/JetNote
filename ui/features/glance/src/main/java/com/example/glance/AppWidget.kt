package com.example.glance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.text.Text
import dagger.hilt.EntryPoints
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach

class AppWidget:GlanceAppWidget() {

    @Composable
    override fun Content() {

        val ctx = LocalContext.current.applicationContext
        val viewModel = EntryPoints.get(
            ctx,
            EntryPoint::class.java
        ).vm()

        val notes = viewModel.getAllNotes()

        LazyColumn {
            items(notes) {
                it.note.title?.let { it1 -> Text(text = it1) }
            }
        }
    }

}