package city.zouitel.audios.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import city.zouitel.audios.utils.ControlButton
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.DataStoreVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MiniMediaPlayer(
    exoViewModule: MediaPlayerViewModel = koinViewModel(),
    datastoreVM: DataStoreVM = koinViewModel(),
    localMediaUid: String?
) {
    val context = LocalContext.current
    val mediaFile = context.filesDir.path + "/${Cons.AUDIOS}/" + localMediaUid + "." + Cons.MP3
    var processState by remember { mutableFloatStateOf(0f) }
    val isPlaying = remember { mutableStateOf(false) }
    val currentLayout by datastoreVM.getLayout.collectAsState()
    val scope = rememberCoroutineScope()

    scope.launch {
        while (isPlaying.value && processState <= 1f) {
            delay(exoViewModule.getMediaDuration(context, mediaFile) / 100)
            processState += .011f
        }
        when {
            processState >= 1f -> {
                isPlaying.value = false
                processState = 0f
            }
        }
    }

    if (isPlaying.value) exoViewModule.playMedia(mediaFile) else exoViewModule.pauseMedia(mediaFile)

    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(.6f, .6f, .6f, .5f)
        )
    ) {
        Row {
            AdaptingRow(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                if (currentLayout == LIST) {
                    ControlButton(isPlaying)

                    LinearProgressIndicator(
                        progress = processState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(7.dp),
                        trackColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        ControlButton(isPlaying)

                        CircularProgressIndicator(
                            progress = processState,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(end = 7.dp),
                    text = exoViewModule.formatLong(
                        exoViewModule.getMediaDuration(context, mediaFile)
                    )
                )
            }
        }
    }
}