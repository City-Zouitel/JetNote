package city.zouitel.audios.ui

import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audios.media.AudioRepository
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AudioScreenModel (
    private val exoBuilder : ExoPlayerImpl,
    private val repository: AudioRepository,
    private val recPath: String
): ScreenModel {

    var getMediaDuration = mutableLongStateOf(0L)
        private set

    var audioAmplitudes = mutableStateListOf<Int>()
        private set

    var rec_path = derivedStateOf { recPath }
        private set

    fun playMedia(mediaUri: String) {
        screenModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).play()
        }
    }

    fun pauseMedia(mediaUri: String) {
        screenModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).pause()
        }
    }

    fun playStreamMedia(mediaUri: String) {
        screenModelScope.launch {
            exoBuilder.prepareStreamMediaPlayer(mediaUri).play()
        }
    }
    fun pauseStreamMedia(mediaUri: String) {
        screenModelScope.launch {
            exoBuilder.prepareStreamMediaPlayer(mediaUri).pause()
        }
    }

    fun getMediaDuration(context: Context, path: String):Long {
        screenModelScope.launch {
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