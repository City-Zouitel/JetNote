package city.zouitel.audio.ui.component

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audio.audio.AudioRepository
import city.zouitel.audio.mapper.AudioMapper
import city.zouitel.audio.model.Audio
import city.zouitel.audio.player.PlaybackManager
import city.zouitel.audio.state.UiState
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.logic.withFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AudioScreenModel(
    observeAll: AudioUseCase.ObserveAll,
    private val _observeByUid_: AudioUseCase.ObserveByUid,
    private val delete: AudioUseCase.DeleteById,
    private val player: PlaybackManager,
    private val repo: AudioRepository,
    private val mapper: AudioMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    internal val uiState: StateFlow<UiState> = _uiState
        .withFlow(UiState())

    private val _observeAll: MutableStateFlow<List<Audio?>> = MutableStateFlow(emptyList())
    val observeAll: StateFlow<List<Audio?>> = _observeAll
        .withFlow(emptyList()) {
            screenModelScope.launch(Dispatchers.IO) {
                observeAll().collect { audios ->
                    this@AudioScreenModel._observeAll.value = mapper.fromDomain(audios)
                }
            }
        }

    private val _observeByUid: MutableStateFlow<Audio?> = MutableStateFlow(null)
    val observeByUid: StateFlow<Audio?> = _observeByUid
        .withFlow(null)

    private var currentAudio = MutableStateFlow<Audio?>(null)

    fun initializeUid(uid: String) {
        screenModelScope.launch(Dispatchers.IO) {
            _observeByUid_(uid).collect {
                runCatching {
                    _observeByUid.value = mapper.fromDomain(it)
                }.onSuccess {
                    currentAudio.value = _observeByUid.value
                    loadAudio(_observeByUid.value?.uri ?: "")
                }
            }
        }
    }

    fun initializeController() {
        player.initializeController()
    }

    fun playbackState() {
        screenModelScope.launch {
            when {
                _uiState.value.isPlaying -> player.pause()
                else -> player.play()
            }
        }
    }

    fun updateProgress(progress: Float) {
        val position = currentAudio.value?.duration?.times(progress)?.toLong()
        player.seekTo(position ?: 0L)
        _uiState.value = _uiState.value.copy(progress = progress)
    }

    internal fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }

    private fun loadAudio(uri: String) {
        screenModelScope.launch {
            runCatching {
                currentAudio.value = repo.loadAudioByUri(uri) ?: return@launch
                currentAudio.value?.let { player.setAudio(it) }
                launch {
                    currentAudio.value?.let {
                        val amplitudes = repo.loadAudioAmplitudes(it.path)
                        _uiState.value = _uiState.value.copy(amplitudes = amplitudes)
                    }
                }
                launch { observePlaybackEvents() }
                _uiState.value = _uiState.value.copy(
                    displayName = currentAudio.value?.nameWithoutFormat ?: "",
                )
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private suspend fun observePlaybackEvents() {
        player.events.collectLatest {
            when(it) {
                is PlaybackManager.Event.PositionChanged -> updatePlaybackProgress(it.position)
                is PlaybackManager.Event.PlayingChanged -> updatePlayingState(it.isPlaying)
                is PlaybackManager.Event.IsLoading -> updateLoadingState(it.isLoading)
                is PlaybackManager.Event.CurrentPath -> {}
            }
        }
    }

    private fun updatePlaybackProgress(position: Long) {
        val audio = currentAudio.value ?: return
        _uiState.value = uiState.value.copy(progress = position.toFloat() / audio.duration)
    }

    private fun updatePlayingState(isPlaying: Boolean) {
        _uiState.value = uiState.value.copy(isPlaying = isPlaying)
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    fun deleteById(id: Long) {
        screenModelScope.launch {
            delete(id)
        }
    }
}