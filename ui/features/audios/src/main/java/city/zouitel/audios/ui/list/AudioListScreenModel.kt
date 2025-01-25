package city.zouitel.audios.ui.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.audio.toUiState
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.model.Audio
import city.zouitel.audios.state.AudioListUiState
import city.zouitel.domain.usecase.AudioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AudioListScreenModel(
    private val audioRepository: AudioRepository,
    private val addAudio: AudioUseCase.AddAudio,
    private val updateAudio: AudioUseCase.UpdateAudio,
    private val deleteAudio: AudioUseCase.DeleteAudio,
    private val audioMapper: AudioMapper,
): ScreenModel {

    private val _audioListUiState: MutableStateFlow<AudioListUiState> = MutableStateFlow(
        AudioListUiState()
    )
    internal val audioListUiState: StateFlow<AudioListUiState> = _audioListUiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            AudioListUiState()
        )

    init {
        loadAudioFiles()
    }

    private fun loadAudioFiles(query: String? = null) {
        screenModelScope.launch(Dispatchers.IO) {
            runCatching {
                _audioListUiState.value = _audioListUiState.value.copy(isLoadingAudios = true)
                val audioFiles = audioRepository.loadAudioFiles(query ?: _audioListUiState.value.searchQuery)
                    .map {
                        it.toUiState {
                            if (audioListUiState.value.newAudio) {
                                addAudioItem(it.copy(uid = audioListUiState.value.currentId))
                            } else {
                                updateAudioItem(it)
                            }
                        }
                    }
                _audioListUiState.value = _audioListUiState.value.copy(audioFiles = audioFiles)
            }.onFailure {
                it.printStackTrace()
            }.onSuccess {
                _audioListUiState.value = _audioListUiState.value.copy(isLoadingAudios = false)
            }
        }
    }

    private fun addAudioItem(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            addAudio.invoke(audioMapper.toDomain(audio))

            delay(500).runCatching {
                audioListUiState.value.bottomSheetNavigator?.hide()
            }
        }
    }

    private fun updateAudioItem(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            updateAudio.invoke(audioMapper.toDomain(audio))

            delay(500).runCatching {
                audioListUiState.value.bottomSheetNavigator?.hide()
            }
        }
    }

    fun deleteAudio(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {

            deleteAudio.invoke(audioMapper.toDomain(audio))
            }
    }

    fun updateSearchQuery(query: String) {
        _audioListUiState.value = _audioListUiState.value.copy(searchQuery = query)
        loadAudioFiles(query)
    }

    fun updateId(uid: String): AudioListScreenModel {
        screenModelScope.launch {
            _audioListUiState.value = _audioListUiState.value.copy(currentId = uid)
        }
        return this
    }

    fun updateBottomSheetNavigator(bottomSheetNavigator: BottomSheetNavigator?): AudioListScreenModel {
        screenModelScope.launch {
            _audioListUiState.value = _audioListUiState.value.copy(bottomSheetNavigator = bottomSheetNavigator)
        }
        return this
    }

    fun updateNavigator(navigator: Navigator?) {
        screenModelScope.launch {
            _audioListUiState.value = _audioListUiState.value.copy(navigator = navigator)
        }
    }

    fun updateNewAudio(isNew: Boolean) {
        screenModelScope.launch {
            _audioListUiState.value = _audioListUiState.value.copy(newAudio = isNew)
        }
    }
}