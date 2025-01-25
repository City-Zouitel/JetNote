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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.audios.model.Audio
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.CommonSwipeItem
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.model.AmplitudeType
import com.linc.audiowaveform.model.WaveformAlignment

data class BasicAudioScreen(val uid: String, val audio: Audio): Screen {
    @Composable
    override fun Content() {
        BasicAudio(audioModel = getScreenModel())
    }

    @Composable
    @SuppressLint("CoroutineCreationDuringComposition")
    private fun BasicAudio(audioModel: AudioScreenModel) {
        val uiState by remember(audioModel, audioModel::uiState).collectAsState()
        val playbackIconState = remember(uiState.isPlaying) {
            if (uiState.isPlaying) CommonIcons.PAUSE_ICON_24 else CommonIcons.PLAY_ICON_24
        }

        LaunchedEffect(true) {
            audioModel.initializeUid(uid)
        }

//        scope.launch {
//            while (uiState.isPlaying && processState <= 1f) {
//                delay(audio.duration / 100)
//                processState += .01f
//            }
//            when {
//                processState >= 1f -> {
//                    processState = 0f
//                }
//            }
//        }

//        LaunchedEffect(uiState.isPlaying) {
//            if (uiState.isPlaying) audioModel.playbackState() else audioModel.pause()
//        }

        CommonSwipeItem(
            enableRightDirection = false,
            onSwipeLeft = {
//                File(audio.path).delete()
            }
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
                                painter = painterResource(playbackIconState),
                                null,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable {
                                        audioModel.playbackState()
                                    },
                                tint = Color.White
                            )

                            AudioWaveform(
                                modifier = Modifier.weight(1f),
                                amplitudes = uiState.amplitudes,
                                progress = uiState.progress,
                                onProgressChange = { audioModel.updateProgress(it) },
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
                                text = audioModel.formatLong(audio.duration),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}