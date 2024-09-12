package city.zouitel.widget.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.media.model.NoteAndMedia
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.media.ui.NoteAndMediaScreenModel
import city.zouitel.widget.model.WidgetNote

class AppWidget: GlanceAppWidget(), Screen {

    override suspend fun provideGlance(context: Context, id: GlanceId) = provideContent {
        this.Content()
    }

    @SuppressLint("RestrictedApi")
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val widgetModel = getScreenModel<WidgetScreenModel>()
        val mediaModel = getScreenModel<MediaScreenModel>()
        val noteAndMediaModel = getScreenModel<NoteAndMediaScreenModel>()
        val notes: List<WidgetNote>? by remember(widgetModel, widgetModel::allNotesById).collectAsState()
        val observeMedias by remember(mediaModel, mediaModel::allMedias).collectAsState()
        val observeNotesAndMedia by remember(noteAndMediaModel, noteAndMediaModel::getAllNotesAndMedia).collectAsState()

        LazyColumn(
            modifier = GlanceModifier
        ) {
            items(
                items = notes ?: emptyList()
            ) { entity ->
                val filteredMedias = observeMedias.filter {
                    observeNotesAndMedia.contains(
                        NoteAndMedia(entity.dataEntity.uid, it.id)
                    )
                }
                val pagerState = rememberPagerState { filteredMedias.size }

                Column {
                    Row(
                        modifier = GlanceModifier
                            .background(ColorProvider(Color(entity.dataEntity.color)))
                            .fillMaxWidth()
                            .cornerRadius(15.dp)
                    ) {
//                        if (filteredMedias.isNotEmpty()) {
//                            HorizontalPager(
//                                state = pagerState,
//                                modifier = GlanceModifier.background(ColorProvider(Color(entity.dataEntity.color))) as Modifier
//                            ) { index ->
//                                BadgedBox(
//                                    modifier = GlanceModifier
//                                        .fillMaxWidth()
//                                        .background(Color(entity.dataEntity.color)) as Modifier,
//                                    badge = {
//                                        if (filteredMedias.count() > 1) {
//                                            Badge(
//                                                modifier = GlanceModifier.padding(3.dp) as Modifier,
//                                                contentColor = Color.White.copy(alpha = .5f),
//                                                containerColor = Color.Black.copy(alpha = .5f)
//                                            ) {
//                                                Text(text = "${index + 1}/${filteredMedias.count()}")
//                                            }
//                                        }
//                                    }
//                                ) {
//                                    Card(
//                                        shape = AbsoluteRoundedCornerShape(15.dp),
//                                        elevation = CardDefaults.cardElevation()
//                                    ) {
//                                        runCatching {
//                                            AsyncImage(
//                                                model = ImageRequest.Builder(context)
//                                                    .data(filteredMedias[index].path)
//                                                    .build(),
//                                                contentDescription = null
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }

                        Spacer(GlanceModifier.width(15.dp))

                        Column {
                            Text(
                                text = entity.dataEntity.title ?: "",
                                style = androidx.glance.text.TextStyle(
                                    fontSize = 19.sp,
                                    color = ColorProvider(Color(entity.dataEntity.textColor))
                                ),
                                modifier = GlanceModifier.padding(3.dp)
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