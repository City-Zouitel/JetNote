package city.zouitel.audios.ui.list

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
import kotlinx.coroutines.launch

class AudioListScreenModel(
    private val audioRepository: AudioRepository,
    private val addAudio: AudioUseCase.AddAudio,
    private val addNoteAndAudio: NoteAndAudioUseCase.AddNoteAndAudio,
    private val deleteAudio: AudioUseCase.DeleteAudio,
    private val audioMapper: AudioMapper,
    private val noteAndAudioMapper: NoteAndAudioMapper
): ScreenModel {

    var AudioListUiState: AudioListUiState by mutableStateOf(AudioListUiState())
        private set

    var noteUid: String? = null

    private var loadAudioJob: Job? = null

    var bottomSheetNavigator: BottomSheetNavigator? by mutableStateOf(null)

    fun updateSearchQuery(query: String) {
        AudioListUiState = AudioListUiState.copy(searchQuery = query)
        loadAudioFiles(query)
    }

    init {
        loadAudioFiles()
    }

    private fun loadAudioFiles(query: String? = null) {
        loadAudioJob?.cancel()
        loadAudioJob = screenModelScope.launch {
            runCatching {
                AudioListUiState = AudioListUiState.copy(isLoadingAudios = true)
                val audioFiles = audioRepository.loadAudioFiles(query ?: AudioListUiState.searchQuery)
                    .map {
                        it.toUiState {
                            // onClick.
                            onSelectAudioItem(it)
                        }
                    }
                AudioListUiState = AudioListUiState.copy(audioFiles = audioFiles)
            }.onFailure {
                it.printStackTrace()
            }.onSuccess {
                AudioListUiState = AudioListUiState.copy(isLoadingAudios = false)
            }
        }
    }

    private fun onSelectAudioItem(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            addAudio.invoke(audioMapper.toDomain(audio))
            addNoteAndAudio.invoke(
                noteAndAudioMapper.toDomain(
                    InNoteAndAudio(noteUid = noteUid ?: "0", audioId = audio.id)
                )
            )
            delay(500).runCatching {
                bottomSheetNavigator?.hide()
            }
        }
    }

    fun deleteAudio(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            deleteAudio.invoke(audioMapper.toDomain(audio))
        }
    }
}