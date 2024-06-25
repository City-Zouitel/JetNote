package city.zouitel.audios.ui.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import city.zouitel.audios.state.AudioListUiState
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.audio.toUiState
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.mapper.NoteAndAudioMapper
import city.zouitel.audios.model.Audio
import city.zouitel.audios.model.NoteAndAudio
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
    getAllNotesAndAudios: NoteAndAudioUseCase.GetAllNotesAndAudios,
    private val audioRepository: AudioRepository,
    private val addAudio: AudioUseCase.AddAudio,
    private val updateAudio: AudioUseCase.UpdateAudio,
    private val addNoteAndAudio: NoteAndAudioUseCase.AddNoteAndAudio,
    private val deleteAudio: AudioUseCase.DeleteAudio,
    private val audioMapper: AudioMapper,
    private val noteAndAudioMapper: NoteAndAudioMapper,
    private val updateNoteAndAudio: NoteAndAudioUseCase.UpdateNoteAndAudio,
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

    private val _allNotesAndAudios: MutableStateFlow<List<NoteAndAudio>> = MutableStateFlow(
        emptyList()
    )
    private val allNoteAndAudio: StateFlow<List<NoteAndAudio>> = _allNotesAndAudios
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    private var loadAudioJob: Job? = null

    init {
        loadAudioFiles()

        screenModelScope.launch(Dispatchers.IO) {
            getAllNotesAndAudios.invoke().collect { notesAndAudio ->
                _allNotesAndAudios.value = noteAndAudioMapper.fromDomain(notesAndAudio)
            }
        }
    }

    private fun loadAudioFiles(query: String? = null) {
        screenModelScope.launch(Dispatchers.IO) {
            runCatching {
                _audioListUiState.value = _audioListUiState.value.copy(isLoadingAudios = true)
                val audioFiles = audioRepository.loadAudioFiles(query ?: _audioListUiState.value.searchQuery)
                    .map {
                        it.toUiState {
                            if (audioListUiState.value.newAudio) {
                                addAudioItem(it)
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

    private fun updateAudioItem(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            updateAudio.invoke(audioMapper.toDomain(audio))
            updateNoteAndAudio.invoke(
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
            if (allNoteAndAudio.value.size == 1) {
            deleteAudio.invoke(audioMapper.toDomain(audio))
            }
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