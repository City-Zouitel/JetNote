package city.zouitel.audios.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.audios.model.Audio
import city.zouitel.audios.model.NoteAndAudio
import city.zouitel.audios.ui.list.AudioListScreen
import city.zouitel.audios.ui.list.AudioListScreenModel
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Icons
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.model.AmplitudeType
import com.linc.audiowaveform.model.WaveformAlignment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import java.io.File

data class BasicAudioScreen(val id: String, val audio: Audio): Screen {
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val audioModel = getScreenModel<AudioScreenModel>()
        val audioListScreen = getScreenModel<AudioListScreenModel>()
        val noteAndAudioScreenModel = getScreenModel<NoteAndAudioScreenModel>()

        val scope = rememberCoroutineScope()
        var processState by remember { mutableFloatStateOf(0f) }
        val playingState = remember { mutableStateOf(false) }
        val swipeState = rememberSwipeableActionsState()
        val iconState = remember(playingState.value) {
            mutableIntStateOf(if (playingState.value) Icons.PAUSE_CIRCLE_FILLED_ICON_24 else Icons.PLAY_CIRCLE_FILLED_ICON_24)
        }

        LaunchedEffect(audio.path) {
            audioModel.loadAudioAmplitudes(audio.path)
        }

        scope.launch {

            while (playingState.value && processState <= 1f) {
                delay(audioModel.getMediaDuration(audio.path) / 100)
                processState += .011f
            }

            when {
                processState >= 1f -> {
                    playingState.value = false
                    processState = 0f
                }
            }
        }

        if (playingState.value) audioModel.playMedia(audio.path) else audioModel.pauseMedia(audio.path)

        val swipeAction = SwipeAction(
            onSwipe = {
                File(audio.path).delete()
                audioListScreen.deleteAudio(audio)
                noteAndAudioScreenModel.deleteNoteAndAudio(
                    NoteAndAudio(id, audio.id)
                )
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
                Column {
                    Row {
                        CommonRow(
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp)
                                .height(80.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = iconState.intValue),
                                null,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable {
                                        playingState.value = !playingState.value
                                    },
                                tint = Color.White
                            )

                            AudioWaveform(
                                modifier = Modifier.weight(1f),
                                amplitudes = audioModel.audioAmplitudes.toList(),
                                progress = processState,
                                onProgressChange = { processState = it },
                                waveformAlignment = WaveformAlignment.Center,
                                amplitudeType = AmplitudeType.Min,
                                style = Fill,
                                progressBrush = Brush.horizontalGradient(listOf(Color(0xFF136FC3), Color(0xFF76EF66))),
                                spikePadding = 3.dp,
                                spikeRadius = 3.dp,
                                spikeWidth = 3.dp,
                            )
                        }
                    }
                    Row {
                        CommonRow(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = audio.nameWithoutFormat)

                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = audioModel.formatLong(
                                    audioModel.getMediaDuration(audio.path)
                                ),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}