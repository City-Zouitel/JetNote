package city.zouitel.audios.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import city.zouitel.audios.state.AudioListUiState
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.audio.toUiState
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.mapper.NoteAndAudioMapper
import city.zouitel.audios.model.Audio
import city.zouitel.audios.model.NoteAndAudio as InNoteAndAudio
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.NoteAndAudioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AudioListScreenModel(
    private val audioRepository: AudioRepository,
    private val addAudio: AudioUseCase.AddAudio,
    private val addNoteAndAudio: NoteAndAudioUseCase.AddNoteAndAudio,
    private val deleteAudio: AudioUseCase.DeleteAudio,
    private val audioMapper: AudioMapper,
    private val noteAndAudioMapper: NoteAndAudioMapper
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

    private var loadAudioJob: Job? = null

    init {
        loadAudioFiles()
    }

    private fun loadAudioFiles(query: String? = null) {
        loadAudioJob?.cancel()
        loadAudioJob = screenModelScope.launch {
            runCatching {
                _audioListUiState.value = _audioListUiState.value.copy(isLoadingAudios = true)
                val audioFiles = audioRepository.loadAudioFiles(query ?: _audioListUiState.value.searchQuery)
                    .map {
                        it.toUiState {
                            // onClick.
                            onSelectAudioItem(it)
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

    private fun onSelectAudioItem(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            addAudio.invoke(audioMapper.toDomain(audio))
            addNoteAndAudio.invoke(
                noteAndAudioMapper.toDomain(
                    InNoteAndAudio(noteUid = audioListUiState.value.currentId, audioId = audio.id)
                )
            )
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

    fun updateId(id: String): AudioListScreenModel {
        screenModelScope.launch {
            _audioListUiState.value = _audioListUiState.value.copy(currentId = id)
        }
        return this
    }

    fun updateBottomSheetNavigator(bottomSheetNavigator: BottomSheetNavigator?) {
        screenModelScope.launch {
            _audioListUiState.value = _audioListUiState.value.copy(bottomSheetNavigator = bottomSheetNavigator)
        }
    }
}