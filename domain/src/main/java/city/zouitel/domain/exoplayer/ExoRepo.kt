@file:Suppress("DEPRECATION")

package city.zouitel.domain.exoplayer

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer

interface ExoRepo {
    val getDuration : Long
   suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer
   suspend fun getMediaDuration(path: String): Long

}