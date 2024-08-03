package city.zouitel.media.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.media.model.NoteAndMedia
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale

data class MediaScreen(val id: String, val backgroundColor: Int = 0): Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {

        Media(
            mediaModel = getScreenModel<MediaScreenModel>(),
            noteAndMediaModel = getScreenModel<NoteAndMediaScreenModel>()
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Media(
        mediaModel: MediaScreenModel,
        noteAndMediaModel: NoteAndMediaScreenModel
    ) {
        val context = LocalContext.current
        val vibe = LocalHapticFeedback.current

        val observeMedias by remember(mediaModel, mediaModel::allMedias).collectAsState()
        val observeNotesAndMedia by remember(
            noteAndMediaModel,
            noteAndMediaModel::getAllNotesAndMedia
        ).collectAsState()

        val filteredMedias = observeMedias.filter {
            observeNotesAndMedia.contains(
                NoteAndMedia(id, it.id)
            )
        }

        val pagerState = rememberPagerState { filteredMedias.size }

        if (filteredMedias.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(backgroundColor))
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { index ->
                    BadgedBox(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(backgroundColor)),
                        badge = {
                            if (filteredMedias.count() > 1) {
                                Badge(
                                    modifier = Modifier.padding(15.dp),
                                    contentColor = Color.White.copy(alpha = .5f),
                                    containerColor = Color.Black.copy(alpha = .5f)
                                ) {
                                    Text(text = "${index + 1}/${filteredMedias.count()}")
                                }
                            }
                        }
                    ) {
                        Card(
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            elevation = CardDefaults.cardElevation(),
                            modifier = Modifier.combinedClickable(
                                onLongClick = {
                                    vibe.performHapticFeedback(HapticFeedbackType.LongPress)

                                    mediaModel.deleteMedia(filteredMedias[index])
                                    noteAndMediaModel.deleteNoteAndMedia(NoteAndMedia(id, filteredMedias[index].id))
                                }
                            ) { /*do nothing..*/ }
                        ) {
                            runCatching {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(filteredMedias[index].path)
                                        .build(),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}