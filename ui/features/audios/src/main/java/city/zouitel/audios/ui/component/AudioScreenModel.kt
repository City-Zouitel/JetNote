package city.zouitel.audios.ui.component

import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.model.Audio
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import city.zouitel.domain.usecase.AudioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AudioScreenModel (
    getAllAudios: AudioUseCase.GetAllAudios,
    private val exoBuilder : ExoPlayerImpl,
    private val repository: AudioRepository,
    private val audioMapper: AudioMapper,
    private val recPath: String
): ScreenModel {

    var getMediaDuration = mutableLongStateOf(0L)
        private set

    var audioAmplitudes = mutableStateListOf<Int>(1,3,2,4,6,5,7,9,8,1,3,2,4,5,6,7,9,8,1,3,2,4,5,6,7,8,9,1,3,2,4,6,5,7,9,8,1,3,2,4,6,5,7,8,9,1,3,2,4,5,6,7,9,8)
        private set

    var rec_path = derivedStateOf { recPath }
        private set

    private val _allAudios = MutableStateFlow<List<Audio>>(emptyList())
    val allAudios: StateFlow<List<Audio>> = _allAudios.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAllAudios.invoke().collect { list ->
                _allAudios.value = list.map { audio -> audioMapper.toView(audio) }
            }
        }
    }

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

    fun seekMedia(mediaUri: String, value: Long) {
        screenModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).seekTo(value)
        }
    }

    fun getMediaDuration(path: String):Long {
        screenModelScope.launch {
            getMediaDuration.longValue = exoBuilder.getMediaDuration(path)
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