package com.example.jetnote.ui.media_player_screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnote.cons.*
import com.example.jetnote.ds.DataStore
import com.example.jetnote.icons.PAUSE_CIRCLE_FILLED_ICON
import com.example.jetnote.icons.PLAY_CIRCLE_FILLED_ICON
import com.example.jetnote.icons.TRASH_ICON
import com.example.jetnote.ui.AdaptingRow
import com.example.jetnote.vm.MediaPlayerVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NoteMediaPlayer(
    exoViewModule: MediaPlayerVM = hiltViewModel(),
    localMediaUid: String?
) {
    val context = LocalContext.current

    val processState = remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val isPlaying = remember { mutableStateOf(false) }
    val mediaFile = context.filesDir.path + "/$AUDIO_FILE/" + localMediaUid + "." + MP3

    //
    val noteDataStore = DataStore(LocalContext.current)

    // the true value is 'list' layout by default and false is 'grid'.
    val currentLayout = noteDataStore.getLayout.collectAsState(true)


//    val exoMedia = exoViewModule.prepareMediaPlayer(mediaFile)
    scope.launch {
        while(isPlaying.value && processState.value <= 1f) {
            delay(exoViewModule.getMediaDuration(context,mediaFile) / 100)
            processState.value += .011f
        }
        when  {
            processState.value >= 1f -> {
                isPlaying.value = false
                processState.value = 0f
            }
        }
    }

    if (isPlaying.value) exoViewModule.playMedia(mediaFile) else exoViewModule.pauseMedia(mediaFile)

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
                if (currentLayout.value){
                        Icon(
                            painter = painterResource(
                                id = if (isPlaying.value) PAUSE_CIRCLE_FILLED_ICON else PLAY_CIRCLE_FILLED_ICON
                            ),
                            null,
                            modifier = Modifier.clickable {
                                isPlaying.value = !isPlaying.value
                            }
                        )

                    LinearProgressIndicator(
                        progress = processState.value,
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
                                    id = if (isPlaying.value) PAUSE_CIRCLE_FILLED_ICON else PLAY_CIRCLE_FILLED_ICON
                                ),
                                null,
                                modifier = Modifier
                                    .padding(start = 3.dp)
                                    .clickable {
                                        isPlaying.value = !isPlaying.value
                                    }
                            )
                        CircularProgressIndicator(progress = processState.value,
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
                                context.filesDir.path + "/" + AUDIO_FILE,
                                "$localMediaUid.$MP3"
                            ).delete()
                        }
                )
            }
        }
    }
}