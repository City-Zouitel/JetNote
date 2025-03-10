package city.zouitel.audios.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.audios.model.Audio
import city.zouitel.systemDesign.CommonConstants.LIST
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.DataStoreScreenModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MiniAudioPlayer(
    dataStoreModel: DataStoreScreenModel,
    audioScreenModel: AudioScreenModel,
    audio: Audio
) {
    var processState by remember { mutableFloatStateOf(0f) }
    val isPlaying = remember { mutableStateOf(false) }
    val currentLayout by dataStoreModel.getLayout.collectAsState()
    val scope = rememberCoroutineScope()

    scope.launch {
        while (isPlaying.value && processState <= 1f) {
            delay(audio.duration / 100)
            processState += .011f
        }
        when {
            processState >= 1f -> {
                isPlaying.value = false
                processState = 0f
            }
        }
    }

//    if (isPlaying.value) audioScreenModel.playbackState() else audioScreenModel.pause()

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
            ) {
                if (currentLayout == LIST) {
                    Icon(
                        painter = painterResource(
                            id = if (isPlaying.value) CommonIcons.PAUSE_CIRCLE_FILLED_ICON else CommonIcons.PLAY_CIRCLE_FILLED_ICON
                        ),
                        null,
                        modifier = Modifier
                            .clickable {
                                isPlaying.value = !isPlaying.value
                            },
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )

                    LinearProgressIndicator(
                        progress = { processState },
                        modifier = Modifier
                            .weight(1f)
                            .padding(7.dp),
                        trackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        strokeCap = StrokeCap.Round
                    )

                } else {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isPlaying.value) CommonIcons.PAUSE_CIRCLE_FILLED_ICON else CommonIcons.PLAY_CIRCLE_FILLED_ICON
                            ),
                            null,
                            modifier = Modifier
                                .clickable {
                                    isPlaying.value = !isPlaying.value
                                },
                            tint = MaterialTheme.colorScheme.surfaceVariant
                        )

                        CircularProgressIndicator(
                            progress = { processState },
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(end = 7.dp),
                    text = audioScreenModel.formatLong(audio.duration),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}