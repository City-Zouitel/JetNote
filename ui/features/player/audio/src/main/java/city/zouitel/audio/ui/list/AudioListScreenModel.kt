package city.zouitel.audio.ui.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import city.zouitel.audio.audio.AudioRepository
import city.zouitel.audio.audio.toUiState
import city.zouitel.audio.mapper.AudioMapper
import city.zouitel.audio.model.Audio
import city.zouitel.audio.state.ListUiState
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.logic.withFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioListScreenModel(
    private val repo: AudioRepository,
    private val insert: AudioUseCase.Insert,
    private val audioMapper: AudioMapper,
): ScreenModel {

    private val _ListUiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState())
    internal val listUiState: StateFlow<ListUiState> = _ListUiState
        .withFlow(ListUiState())

    init {
        loadAudioFiles()
    }

    private fun loadAudioFiles(query: String? = null) {
        screenModelScope.launch(Dispatchers.IO) {
            runCatching {
                _ListUiState.value = _ListUiState.value.copy(isLoadingAudios = true)
                val audioFiles = repo.loadAudioFiles(query ?: _ListUiState.value.searchQuery)
                    .map {
                        it.toUiState {
                            if (listUiState.value.newAudio) {
                                addAudioItem(it.copy(uid = listUiState.value.currentId))
                            } else {
                                screenModelScope.launch {
                                    delay(300).runCatching {
                                        listUiState.value.bottomSheetNavigator?.hide()
                                    }
                                }
                            }
                        }
                    }
                _ListUiState.value = _ListUiState.value.copy(audioFiles = audioFiles)
            }.onFailure {
                it.printStackTrace()
            }.onSuccess {
                _ListUiState.value = _ListUiState.value.copy(isLoadingAudios = false)
            }
        }
    }

    private fun addAudioItem(audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            insert.invoke(audioMapper.toDomain(audio))

            delay(500).runCatching {
                listUiState.value.bottomSheetNavigator?.hide()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _ListUiState.value = _ListUiState.value.copy(searchQuery = query)
        loadAudioFiles(query)
    }

    fun updateId(uid: String): AudioListScreenModel {
        screenModelScope.launch {
            _ListUiState.value = _ListUiState.value.copy(currentId = uid)
        }
        return this
    }

    fun updateBottomSheetNavigator(bottomSheetNavigator: BottomSheetNavigator?): AudioListScreenModel {
        screenModelScope.launch {
            _ListUiState.value = _ListUiState.value.copy(bottomSheetNavigator = bottomSheetNavigator)
        }
        return this
    }

    fun updateNavigator(navigator: Navigator?) {
        screenModelScope.launch {
            _ListUiState.value = _ListUiState.value.copy(navigator = navigator)
        }
    }

    fun updateNewAudio(isNew: Boolean) {
        screenModelScope.launch {
            _ListUiState.value = _ListUiState.value.copy(newAudio = isNew)
        }
    }
}