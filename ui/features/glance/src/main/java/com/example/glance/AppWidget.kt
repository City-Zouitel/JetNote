package com.example.glance

import android.net.Uri
import android.text.format.DateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.local.model.Entity
import com.example.local.model.Note
import com.example.local.model.NoteAndTodo
import com.example.local.model.Todo
import dagger.hilt.EntryPoints
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import java.io.File
import java.util.*

class AppWidget:GlanceAppWidget() {


    @Composable
    override fun Content() {


        val ctx = LocalContext.current.applicationContext
        val viewModel = EntryPoints.get(
            ctx,
            EntryPoint::class.java
        ).vm()

        val notes = viewModel.getAllNotes()

       WidgetListNotes(items = notes)
    }

    @Composable
    fun WidgetListNotes(
        items: List<Entity>
    ) {
        LazyColumn(
            modifier = GlanceModifier
        ) {
            items(
                items = items
            ) {entity ->
                androidx.glance.layout.Column {
                    Text(
                        text = entity.note.title ?: "",
                        style = androidx.glance.text.TextStyle(
                            fontSize = 19.sp,
                            color = ColorProvider(Color(entity.note.textColor))
                        ),
                        modifier = GlanceModifier.padding(3.dp)
                    )
                    Text(
                        text = entity.note.description ?: "",
                        style = androidx.glance.text.TextStyle(
                            fontSize = 15.sp,
                            color = ColorProvider(Color(entity.note.textColor))
                        ),
                        modifier = GlanceModifier.padding(start = 3.dp, end = 3.dp, bottom = 7.dp)
                    )
                }
            }
        }
    }
}