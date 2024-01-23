package city.zouitel.audios

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MediaPlayerViewModel (
    private val exoBuilder : ExoPlayerImpl,
): ViewModel() {

    var getMediaDuration = mutableLongStateOf(0L)
    private set

    fun playMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).play()
        }
    }

    fun pauseMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).pause()
        }
    }

    fun playStreamMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareStreamMediaPlayer(mediaUri).play()
        }
    }
    fun pauseStreamMedia(mediaUri: String) {
        viewModelScope.launch {
            exoBuilder.prepareStreamMediaPlayer(mediaUri).pause()
        }
    }

    fun getMediaDuration(context: Context, path: String):Long {
        viewModelScope.launch {
            getMediaDuration.longValue = exoBuilder.getMediaDuration(context,path)
        }
        return getMediaDuration.longValue
    }

    internal fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }
}