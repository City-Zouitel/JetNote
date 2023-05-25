package com.example.glance

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import dagger.hilt.EntryPoints

class AppWidget:GlanceAppWidget() {

    @Composable
    override fun Content() {

        val ctx = LocalContext.current.applicationContext

        val viewModel = EntryPoints.get(
            ctx,
            EntryPoint::class.java
        ).vm()

        WidgetListNotes(ctx = ctx, widgetVm = viewModel)
    }

    @Composable
    fun WidgetListNotes(
        ctx: Context,
        widgetVm: WidgetVM,
    ) {
        val notes = widgetVm.getAllEntities()

        LazyColumn(
            modifier = GlanceModifier
        ) {
            items(
                items = notes
            ) { entity ->
                Column {
                    Row(
                        modifier = GlanceModifier
                            .background(ColorProvider(Color(entity.note.color)))
                            .fillMaxWidth()
                            .cornerRadius(15.dp)
                    ) {
                        kotlin.runCatching {
                            Image(
                                ImageProvider(
                                    bitmap = widgetVm::imageDecoder.invoke(ctx, entity.note.uid)
                                ),
                                null,
                                modifier = GlanceModifier
                                    .cornerRadius(15.dp)
                            )
                        }

                        Spacer(GlanceModifier.width(15.dp))

                        Column {
                            Text(
                                text = entity.note.title ?: "",
                                style = androidx.glance.text.TextStyle(
                                    fontSize = 19.sp,
                                    color = ColorProvider(Color(entity.note.textColor))
                                ),
                                modifier = GlanceModifier
                                    .padding(3.dp)
                            )

                            Text(
                                text = entity.note.description ?: "",
                                style = androidx.glance.text.TextStyle(
                                    fontSize = 15.sp,
                                    color = ColorProvider(Color(entity.note.textColor))
                                ),
                                modifier = GlanceModifier
                                    .padding(
                                        start = 3.dp,
                                        end = 3.dp,
                                        bottom = 7.dp
                                    )
                            )
                        }

                    }

                    Spacer(GlanceModifier.height(10.dp))
                }
            }
        }
    }
}