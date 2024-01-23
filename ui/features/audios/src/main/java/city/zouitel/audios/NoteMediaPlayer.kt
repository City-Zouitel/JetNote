package city.zouitel.audios

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons.AUDIOS
import city.zouitel.systemDesign.Cons.MP3
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.PAUSE_CIRCLE_FILLED_ICON
import city.zouitel.systemDesign.Icons.PLAY_CIRCLE_FILLED_ICON
import city.zouitel.systemDesign.Icons.TRASH_ICON
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NoteMediaPlayer(
    exoViewModule: MediaPlayerViewModel = koinViewModel(),
    datastoreVM: DataStoreVM = koinViewModel(),
    localMediaUid: String?
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val mediaFile = context.filesDir.path + "/$AUDIOS/" + localMediaUid + "." + MP3
    var processState by remember { mutableFloatStateOf(0f) }
    var isPlaying by remember { mutableStateOf(false) }

    val currentLayout by datastoreVM.getLayout.collectAsState()

//    val exoMedia = exoViewModule.prepareMediaPlayer(mediaFile)
    scope.launch {
        while(isPlaying && processState <= 1f) {
            delay(exoViewModule.getMediaDuration(context,mediaFile) / 100)
            processState += .011f
        }
        when  {
            processState >= 1f -> {
                isPlaying = false
                processState = 0f
            }
        }
    }

    if (isPlaying) exoViewModule.playMedia(mediaFile) else exoViewModule.pauseMedia(mediaFile)

    Surface(
        modifier = Modifier
            .padding(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row {
            AdaptingRow(modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)) {
                //
                if (currentLayout == "GRID"){
                        Icon(
                            painter = painterResource(
                                id = if (isPlaying) PAUSE_CIRCLE_FILLED_ICON else PLAY_CIRCLE_FILLED_ICON
                            ),
                            null,
                            modifier = Modifier.clickable {
                                isPlaying = !isPlaying
                            }
                        )

                    LinearProgressIndicator(
                        progress = processState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        trackColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) PAUSE_CIRCLE_FILLED_ICON else PLAY_CIRCLE_FILLED_ICON
                                ),
                                null,
                                modifier = Modifier
                                    .padding(start = 3.dp)
                                    .clickable {
                                        isPlaying = !isPlaying
                                    }
                            )
                        CircularProgressIndicator(progress = processState,
                            modifier = Modifier,
                        )
                    }
                }
                //
                Text(
                    text =
                    exoViewModule.formatLong(
                        exoViewModule.getMediaDuration(context,mediaFile)
                    )
                )
                //
                Icon(
                    painter = painterResource(id = TRASH_ICON),
                    null,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            File(
                                context.filesDir.path + "/" + AUDIOS,
                                "$localMediaUid.$MP3"
                            ).delete()
                        }
                )
            }
        }
    }
}