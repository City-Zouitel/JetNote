package com.example.glance.presentation

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.glance.data.NoteStateDefinition
import com.example.glance.model.GlanceNote
import kotlinx.collections.immutable.toImmutableList

class NoteGlanceWidget:GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*>?
        get() = NoteStateDefinition
    @Composable
    override fun Content() {
        val notes = currentState<List<GlanceNote>>().toImmutableList()

        LazyColumn {
            items(notes) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "sdfsdfds")
                }
            }
        }
    }
}