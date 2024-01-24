package city.zouitel.widget.ui

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
import org.koin.androidx.compose.koinViewModel

class AppWidget: GlanceAppWidget() {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<WidgetViewModel>()
        val ctx = LocalContext.current.applicationContext

//        val viewModel = EntryPoints.get(
//            ctx,
//            EntryPoint::class.java
//        ).viewmodel()

        WidgetListNotes(ctx = ctx, widgetViewModel = viewModel)
    }

    @Composable
    fun WidgetListNotes(
        ctx: Context,
        widgetViewModel: WidgetViewModel,
    ) {
        val notes = widgetViewModel.getAllEntities().invoke()

        LazyColumn(
            modifier = GlanceModifier
        ) {
            items(
                items = notes
            ) { entity ->
                Column {
                    Row(
                        modifier = GlanceModifier
                            .background(ColorProvider(Color(entity.dataEntity.color)))
                            .fillMaxWidth()
                            .cornerRadius(15.dp)
                    ) {
                        kotlin.runCatching {
                            Image(
                                ImageProvider(
                                    bitmap = widgetViewModel::imageDecoder.invoke(ctx, entity.dataEntity.uid)
                                ),
                                null,
                                modifier = GlanceModifier
                                    .cornerRadius(15.dp)
                            )
                        }

                        Spacer(GlanceModifier.width(15.dp))

                        Column {
                            Text(
                                text = entity.dataEntity.title ?: "",
                                style = androidx.glance.text.TextStyle(
                                    fontSize = 19.sp,
                                    color = ColorProvider(Color(entity.dataEntity.textColor))
                                ),
                                modifier = GlanceModifier
                                    .padding(3.dp)
                            )

                            Text(
                                text = entity.dataEntity.description ?: "",
                                style = androidx.glance.text.TextStyle(
                                    fontSize = 15.sp,
                                    color = ColorProvider(Color(entity.dataEntity.textColor))
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