package city.zouitel.audios.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Icons
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.model.AmplitudeType
import com.linc.audiowaveform.model.WaveformAlignment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import org.koin.androidx.compose.koinViewModel
import java.io.File

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun NormalMediaPlayer(
    exoViewModule: MediaPlayerViewModel = koinViewModel(),
    localMediaUid: String?,
) {
    val context = LocalContext.current
    val mediaFile = arrayOf(
        context.filesDir.path,
        Cons.REC_DIR,
        localMediaUid + "." + Cons.MP3
    ).joinToString("/")
    var processState by remember { mutableFloatStateOf(0f) }
    val isPlaying = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val swipeState = rememberSwipeableActionsState()

    val tempMedia = "/storage/emulated/0/Download/Vaults One Last Night.mp3"

    scope.launch {
        exoViewModule.loadAudioAmplitudes(tempMedia)

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

    val amplitudes by remember { mutableStateOf(exoViewModule.audioAmplitudes) }

    if (isPlaying.value) exoViewModule.playMedia(tempMedia) else exoViewModule.pauseMedia(tempMedia)

    val swipeAction = SwipeAction(
        onSwipe = {
            File(
                context.filesDir.path + File.pathSeparator + Cons.REC_DIR,
                "$localMediaUid.${Cons.MP3}"
            ).delete()
        },
        icon = {
            Icon(painterResource(Icons.DELETE_OUTLINE_ICON), null)
        },
        background = Color.Red
    )

    SwipeableActionsBox(
        modifier = Modifier.clip(ShapeDefaults.Medium),
        backgroundUntilSwipeThreshold = Color.Transparent,
        swipeThreshold = 100.dp,
        state = swipeState,
        endActions = listOf(swipeAction)
    ) {
        Card(
            modifier = Modifier.padding(10.dp),
            shape = RoundedCornerShape(17.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(.6f, .6f, .6f, .5f)
            )
        ) {
            Row {
                CommonRow(
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .height(80.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isPlaying.value) Icons.PAUSE_CIRCLE_FILLED_ICON_24 else Icons.PLAY_CIRCLE_FILLED_ICON_24
                        ),
                        null,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                isPlaying.value = !isPlaying.value
                            },
                        tint = Color.White
                    )

                    AudioWaveform(
                        modifier = Modifier.weight(1f),
                        amplitudes = amplitudes,
                        progress = processState,
                        onProgressChange = { processState = it },
                        waveformAlignment = WaveformAlignment.Center,
                        style = Fill,
                        amplitudeType = AmplitudeType.Avg,
                        spikePadding = 2.dp,
                        spikeRadius = 2.dp,
                        spikeWidth = 2.dp,
                    )

                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = exoViewModule.formatLong(
                            exoViewModule.getMediaDuration(context, mediaFile)
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}