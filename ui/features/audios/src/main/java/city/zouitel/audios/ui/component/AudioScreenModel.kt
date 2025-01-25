package city.zouitel.audios.ui.component

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.model.Audio
import city.zouitel.audios.player.PlaybackManager
import city.zouitel.audios.state.UiState
import city.zouitel.domain.usecase.AudioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AudioScreenModel (
    observeAll: AudioUseCase.ObserveAll,
    private val _observeByUid_: AudioUseCase.ObserveByUid,
    private val player: PlaybackManager,
    private val repository: AudioRepository,
    private val mapper: AudioMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    internal val uiState: StateFlow<UiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )

    private val _allAudios: MutableStateFlow<List<Audio?>> = MutableStateFlow(emptyList())
    val allAudios: StateFlow<List<Audio?>> = _allAudios
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    private val _observeByUid: MutableStateFlow<Audio?> = MutableStateFlow(null)
    val observeByUid: StateFlow<Audio?> = _observeByUid
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

    private var currentAudio = MutableStateFlow<Audio?>(null)

    init {
        screenModelScope.launch(Dispatchers.IO) {
            observeAll.invoke().collect { audios ->
                _allAudios.value = mapper.fromDomain(audios)
            }
        }
    }

    fun initializeUid(uid: String) {
        screenModelScope.launch(Dispatchers.IO) {
            player.initializeController()
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
                currentAudio.value = repository.loadAudioByUri(uri) ?: return@launch
                currentAudio.value?.let { player.setAudio(it) }
                launch {
                    currentAudio.value?.let {
                        val amplitudes = repository.loadAudioAmplitudes(it.path)
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

    override fun onDispose() {
        super.onDispose()
        player.releaseController()
    }
}