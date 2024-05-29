package city.zouitel.domain.exoplayer

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

@Suppress("DEPRECATION")
class ExoPlayerImpl (
    private val context: Context,
    private val exo: ExoPlayer,
): ExoRepo {

    override suspend fun playMedia() {
        exo.play()
    }

    override suspend fun pauseMedia() {
        exo.pause()
    }

    suspend fun getDuration(): Long = exo.duration


    override suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer {
        exo.setMediaItem(MediaItem.fromUri(File(mediaPath).toUri()))
        exo.prepare()
        return exo
    }

    override suspend fun getMediaDuration(path: String): Long {
        if (!File(path).exists()) return 0L
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, File(path).path.toUri())
            // retriever.setDataSource("uri",HashMap<String, String>()) // for cloud media.
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            retriever.release()
            duration?.toLongOrNull() ?: 0L
        } catch (exception: Exception) {
            0L
        }
    }

    suspend fun getMediaArtist(path: String): String {
        if (!File(path).exists()) return "null"
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, File(path).path.toUri())
            // retriever.setDataSource("uri",HashMap<String, String>()) // for cloud media.
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            retriever.release()
            artist ?: "Unknown"
        } catch (exception: Exception) {
            "Unknown"
        }
    }
}