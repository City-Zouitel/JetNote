package city.zouitel.video

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun SmallVideoPlayer(uri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember(context, uri) {
        ExoPlayer.Builder(context).build().apply {
            try {
                val mediaMetadata = MediaMetadata.Builder()
                    .build()

                val mediaItem = MediaItem.Builder()
                    .setUri(uri)
                    .setMediaMetadata(mediaMetadata)
                    .build()
                setMediaItem(mediaItem)

                prepare()
                volume = 0f
                playbackParameters = PlaybackParameters(0.5f)
                playWhenReady = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
                setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING) // Show buffering indicator
            }
        }, update = { playerView ->
            playerView.player = exoPlayer
        })

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}