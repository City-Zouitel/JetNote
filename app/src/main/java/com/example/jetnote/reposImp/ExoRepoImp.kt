package com.example.jetnote.reposImp

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.core.net.toUri
import com.example.jetnote.repos.ExoRepo
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExoRepoImp  @Inject constructor (
    private val exo: ExoPlayer,
):ExoRepo {

    override val getDuration: Long = 0

    override suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer {
        exo.setMediaItem(
            MediaItem.fromUri(
                File(mediaPath).toUri()
            )
        )
        exo.prepare()
        return exo
    }

    override suspend fun prepareStreamMediaPlayer(mediaPath: String): ExoPlayer {
        exo.setMediaItem(
            MediaItem.fromUri(
                mediaPath
            )
        )
        exo.prepare()
        return exo
    }

    override suspend fun getMediaDuration(context: Context, path: String): Long {
        if (!File(path).exists()) return 0L
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, File(path).path.toUri())
            // retriever.setDataSource("url",HashMap<String, String>()) // for cloud media.
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            retriever.release()
            duration?.toLongOrNull() ?: 0L
        } catch (exception: Exception) {
            0L
        }
    }
}


