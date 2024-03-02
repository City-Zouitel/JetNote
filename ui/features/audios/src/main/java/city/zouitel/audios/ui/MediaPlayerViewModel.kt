package city.zouitel.audios.ui

import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.audios.media.AudioRepository
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MediaPlayerViewModel (
    private val exoBuilder : ExoPlayerImpl,
    private val repository: AudioRepository,
    private val recPath: String
): ViewModel() {

    var getMediaDuration = mutableLongStateOf(0L)
        private set

    var audioAmplitudes = mutableStateListOf<Int>()
        private set

    var rec_path = derivedStateOf { recPath }
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
            getMediaDuration.longValue = exoBuilder.getMediaDuration(context, path)
        }
        return getMediaDuration.longValue
    }

    internal fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }

    suspend fun loadAudioAmplitudes(localAudioPath: String) {
        runCatching {
            val amplitudes = repository.loadAudioAmplitudes(localAudioPath)
            audioAmplitudes.addAll(amplitudes)
        }
    }
}