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
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(uri: Uri, onClick:() -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember(context, uri) { // Add uri as a key
        ExoPlayer.Builder(context).build().apply {
            try {
                val mediaMetadata = MediaMetadata.Builder()
                    .build()

                // Load the local URI using ContentResolver
                val mediaItem = MediaItem.Builder()
                    .setUri(uri)
                    .setMediaMetadata(mediaMetadata)
                    .build()
                setMediaItem(mediaItem)

                // Make sure to use PREPARE_WAIT_FOR_SURFACE_REQUEST to
                // prevent crashing in Compose
                prepare()
                playWhenReady = false
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
                useController = true

                this.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING) // Show buffering indicator

                // Customize the controller
                this.setShowFastForwardButton(false)  // Remove fast forward
                this.setShowRewindButton(false) // remove rewind
                this.setShowNextButton(false) // remove next button
                this.setShowPreviousButton(false) // remove prev button
                this.setControllerAnimationEnabled(false)
                this.setFullscreenButtonClickListener {
                    player?.pause()
                    onClick()
                }
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