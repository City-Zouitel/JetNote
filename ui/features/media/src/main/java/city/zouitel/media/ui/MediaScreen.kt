package city.zouitel.media.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.domain.utils.Action
import city.zouitel.video.VideoPlayer
import coil.compose.AsyncImage

data class MediaScreen(val uid: String, val backgroundColor: Int = 0): Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val mediaModel = getScreenModel<MediaScreenModel>()

        LaunchedEffect(true) {
            mediaModel.initializeUid(uid)
        }

        Media(mediaModel = mediaModel)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Media(mediaModel: MediaScreenModel) {
        val context = LocalContext.current
        val vibe = LocalHapticFeedback.current

        val observeMedias by remember(
            mediaModel,
            mediaModel::observeByUid
        ).collectAsStateWithLifecycle()
        val pagerState = rememberPagerState { observeMedias.size }

        if (observeMedias.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(backgroundColor)),
                modifier = Modifier
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { index ->
                    BadgedBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(Color(backgroundColor))
                            .combinedClickable(
                                onLongClick = {
                                    vibe.performHapticFeedback(HapticFeedbackType.LongPress)
                                    mediaModel.sendAction(Action.DeleteById(observeMedias[index].id))
                                }
                            ) {},
                        badge = {
                            if (observeMedias.count() > 1) {
                                Badge(
                                    modifier = Modifier.padding(15.dp),
                                    contentColor = Color.White.copy(alpha = .5f),
                                    containerColor = Color.Black.copy(alpha = .5f)
                                ) {
                                    Text(text = "${index + 1}/${observeMedias.count()}")
                                }
                            }
                        }
                    ) {
                        Card(
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            elevation = CardDefaults.cardElevation()
                        ) {
                            when (observeMedias[index].isVideo) {
                                true -> {
                                    VideoPlayer(observeMedias[index].uri.toUri()) {
                                        context.startActivity(videoDisplay(observeMedias[index].uri.toUri()))
                                    }
                                }

                                else -> {
                                    AsyncImage(
                                        model = observeMedias[index].uri,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.combinedClickable(
                                            onLongClick = {
                                            }
                                        ) {
//                                            context.startActivity(imageDisplay(observeMedias[index].uri.toUri()))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun imageDisplay(uri: Uri) = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "image/*")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    private fun videoDisplay(uri: Uri) = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "video/*")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
    }
}