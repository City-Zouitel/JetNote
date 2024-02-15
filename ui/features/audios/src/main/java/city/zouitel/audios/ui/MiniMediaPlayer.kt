package city.zouitel.audios.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons
import com.galaxygoldfish.waveslider.WaveSlider
import com.galaxygoldfish.waveslider.WaveSliderDefaults
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
                    Icon(
                        painter = painterResource(
                            id = if (isPlaying.value) Icons.PAUSE_CIRCLE_FILLED_ICON else Icons.PLAY_CIRCLE_FILLED_ICON
                        ),
                        null,
                        modifier = Modifier
                            .clickable {
                                isPlaying.value = !isPlaying.value
                            },
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )

                    WaveSlider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(7.dp),
                        value = processState,
                        onValueChange = { processState = it },
                        animationOptions = WaveSliderDefaults
                            .animationOptions(
                                reverseDirection = true
                            ),
                        colors = WaveSliderDefaults.colors(
                            activeTickColor = MaterialTheme.colorScheme.surfaceTint,
                            thumbColor = MaterialTheme.colorScheme.inverseSurface,
                            activeTrackColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isPlaying.value) Icons.PAUSE_CIRCLE_FILLED_ICON else Icons.PLAY_CIRCLE_FILLED_ICON
                            ),
                            null,
                            modifier = Modifier
                                .clickable {
                                    isPlaying.value = !isPlaying.value
                                },
                            tint = MaterialTheme.colorScheme.surfaceVariant
                        )

                        CircularProgressIndicator(
                            progress = processState,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(end = 7.dp),
                    text = exoViewModule.formatLong(
                        exoViewModule.getMediaDuration(context, mediaFile)
                    ),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}