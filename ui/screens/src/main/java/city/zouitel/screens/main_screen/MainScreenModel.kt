package city.zouitel.screens.main_screen

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.screens.mapper.NoteMapper
import city.zouitel.screens.state.MainScreenUiState
import city.zouitel.tags.model.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("DeferredResultUnused")
class MainScreenModel(
    private val observeByDefault: NoteUseCase.ObserveByDefault,
    private val observeByNewest: NoteUseCase.ObserveByNewest,
    private val observeByOldest: NoteUseCase.ObserveByOldest,
    private val observeByName: NoteUseCase.ObserveByName,
    private val observeByPriority: NoteUseCase.ObserveByPriority,
    private val observeByArchives: NoteUseCase.ObserveArchives,
    private val mapper: NoteMapper
): ScreenModel {
    private val _uiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState
        .withFlow(MainScreenUiState())

    private val _observeDefaults = MutableStateFlow<List<Note>>(emptyList())
    val observeDefaults: StateFlow<List<Note>> = _observeDefaults
        .withFlow(emptyList()){
            withAsync {
                observeByDefault().collect { _observeDefaults.value = mapper.fromDomain(it) }
            }
        }

    private val _observeNewest = MutableStateFlow<List<Note>>(emptyList())
    val observeNewest: StateFlow<List<Note>> = _observeNewest
        .withFlow(emptyList()){
            withAsync {
                observeByNewest().collect { _observeNewest.value = mapper.fromDomain(it) }
            }
        }

    private val _observeOldest = MutableStateFlow<List<Note>>(emptyList())
    val observeOldest: StateFlow<List<Note>> = _observeOldest
        .withFlow(emptyList()){
            withAsync {
                observeByOldest().collect { _observeOldest.value = mapper.fromDomain(it) }
            }
        }

    private val _observeNames = MutableStateFlow<List<Note>>(emptyList())
    val observeNames: StateFlow<List<Note>> = _observeNames
        .withFlow(emptyList()){
            withAsync {
                observeByName().collect { _observeNames.value = mapper.fromDomain(it) }
            }
        }

    private val _observePriorities = MutableStateFlow<List<Note>>(emptyList())
    val observePriorities: StateFlow<List<Note>> = _observePriorities
        .withFlow(emptyList()){
            withAsync {
                observeByPriority().collect { _observePriorities.value = mapper.fromDomain(it) }
            }
        }

    private val _observeArchives = MutableStateFlow<List<Note>>(emptyList())
    val observeArchives: StateFlow<List<Note>> = _observeArchives
        .withFlow(emptyList()){
            withAsync {
                observeByArchives().collect { _observeArchives.value = mapper.fromDomain(it) }
            }
        }

    fun updateSearchTitle(title: String) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(searchTitle = title)
        }
    }

    fun updateSearchTag(tag: Tag?) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(searchTag = tag)
        }
    }

    fun updateSelection(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isSelection = value)
        }
    }

    fun updateSelectedNotes(note: Data) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy().addSelectedNote(note)
        }

    }

    fun clearSelectionNotes() {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedNotes = mutableStateListOf())
        }
    }

    fun updateScreen(isHome: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isHomeScreen = isHome)
        }
    }

    fun updateSelectedNote(uid: String) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedNote = uid)
        }
    }
}