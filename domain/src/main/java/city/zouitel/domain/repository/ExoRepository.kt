package city.zouitel.domain.repository

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer

interface ExoRepository {

    val getDuration: Long

    suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer

    suspend fun prepareStreamMediaPlayer(mediaPath: String): ExoPlayer

    suspend fun getMediaDuration(context: Context, path: String): Long

}