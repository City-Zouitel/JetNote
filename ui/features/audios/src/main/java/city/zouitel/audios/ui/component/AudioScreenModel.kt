package city.zouitel.audios.ui.component

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.model.Audio
import city.zouitel.audios.state.AudioUiState
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.systemDesign.Icons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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

    var uiState: AudioUiState by mutableStateOf(AudioUiState())
        private set

    var rec_path = derivedStateOf { recPath }
        private set

    private val _allAudios = MutableStateFlow<List<Audio>>(emptyList())
    val allAudios = _allAudios.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAllAudios.invoke().collect { list ->
                _allAudios.value = list.map { audio -> audioMapper.toView(audio) }
            }
        }
    }

    suspend fun prepareMediaPlayer(mediaPath: String) {
        runCatching {
            exoBuilder.prepareMediaPlayer(mediaPath)

            val amplitudes = repository.loadAudioAmplitudes(mediaPath)
//            audioAmplitudes.addAll(amplitudes)
            uiState = uiState.copy(amplitudes = amplitudes)
        }
    }

    fun playMedia() {
        screenModelScope.launch {
            exoBuilder.playMedia()
            uiState = uiState.copy(isPlaying = true)
            uiState = uiState.copy(icon = Icons.PAUSE_CIRCLE_FILLED_ICON_24)
        }
    }

    fun pauseMedia() {
        screenModelScope.launch {
            exoBuilder.pauseMedia()
            uiState = uiState.copy(isPlaying = false)
            uiState = uiState.copy(icon = Icons.PLAY_CIRCLE_FILLED_ICON_24)
        }
    }

    fun seekMedia(mediaUri: String, value: Long) {
        screenModelScope.launch {
            exoBuilder.prepareMediaPlayer(mediaUri).seekTo(value)
        }
    }

    fun getMediaArtist(mediaUri: String) =
        screenModelScope.launch(Dispatchers.IO) {
            exoBuilder.getMediaArtist(mediaUri)
        }

    internal fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }
}