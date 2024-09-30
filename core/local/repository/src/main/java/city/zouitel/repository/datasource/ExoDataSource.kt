package city.zouitel.repository.datasource

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer

interface ExoDataSource {

    val getDuration: Long

    suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer

    suspend fun prepareStreamMediaPlayer(mediaPath: String): ExoPlayer

    suspend fun getMediaDuration(context: Context, path: String): Long

}