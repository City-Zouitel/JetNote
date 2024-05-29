@file:Suppress("DEPRECATION")

package city.zouitel.domain.exoplayer

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.flow.Flow

interface ExoRepo {
    suspend fun playMedia()

    suspend fun pauseMedia()

    suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer
    suspend fun getMediaDuration(path: String): Long
}