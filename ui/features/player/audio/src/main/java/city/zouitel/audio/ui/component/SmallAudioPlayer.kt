package city.zouitel.audio.ui.component

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import city.zouitel.audio.model.Audio
import city.zouitel.systemDesign.CommonConstants.LIST
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.DataStoreScreenModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SmallAudioPlayer(
    dataStoreModel: DataStoreScreenModel,
    audioModel: AudioScreenModel,
    audio: Audio
) {
    val scope = rememberCoroutineScope()
    val currentLayout by dataStoreModel.getLayout.collectAsState()
    val uiState by remember(audioModel, audioModel::uiState).collectAsState()
    var isPlaying by remember { mutableStateOf(false) }
    val playbackIconState =
        if (isPlaying) CommonIcons.PAUSE_ICON_24 else CommonIcons.PLAY_ICON_24

    LaunchedEffect(true) {
        audioModel.initializeController()
    }

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
                        painter = painterResource(playbackIconState),
                        null,
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    audioModel.initializeUid(audio.uid)
                                }.invokeOnCompletion {
                                    isPlaying = isPlaying.not()
                                    audioModel.playbackState()
                                }
                            },
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )

                    LinearProgressIndicator(
                        progress = { uiState.progress },
                        modifier = Modifier
                            .weight(1f)
                            .padding(7.dp),
                        trackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        strokeCap = StrokeCap.Round
                    )

                } else {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(playbackIconState),
                            null,
                            modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        audioModel.initializeUid(audio.uid)
                                    }.invokeOnCompletion {
                                        isPlaying = isPlaying.not()
                                        audioModel.playbackState()
                                    }
                                },
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                        CircularProgressIndicator(
                            progress = { uiState.progress },
                            color = MaterialTheme.colorScheme.surfaceBright,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(end = 7.dp),
                    text = audioModel.formatLong(audio.duration),
                    color = MaterialTheme.colorScheme.surfaceBright
                )
            }
        }
    }
}