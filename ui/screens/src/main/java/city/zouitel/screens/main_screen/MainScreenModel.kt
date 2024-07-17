package city.zouitel.screens.main_screen

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.screens.mapper.NoteMapper
import city.zouitel.note.model.Data
import city.zouitel.screens.state.UiState
import city.zouitel.tags.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.note.model.Note as InNote

class MainScreenModel(
    private val getAllById: NoteUseCase.GetAllNotesById,
    private val getAllByOldest: NoteUseCase.GetAllNotesByOldest,
    private val getAllByNewest: NoteUseCase.GetAllNotesByNewest,
    private val getAllByName: NoteUseCase.GetAllNotesByName,
    private val getAllRemoved: NoteUseCase.GetAllRemovedNotes,
    private val getAllByPriority: NoteUseCase.GetAllNotesByPriority,
    private val getAllReminding: NoteUseCase.GetAllRemindingNotes,
    private val mapper: NoteMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState()
    )
    internal val uiState: StateFlow<UiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )

    // for observing the dataEntity changes.
    private val _allNotesById = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesById: StateFlow<List<InNote>> = _allNotesById.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByOldest = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByOldest: StateFlow<List<InNote>> = _allNotesByOldest.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByNewest = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByNewest: StateFlow<List<InNote>> = _allNotesByNewest.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByName = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByName: StateFlow<List<InNote>> = _allNotesByName.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allTrashedNotes = MutableStateFlow<List<InNote>>(emptyList())
    val allTrashedNotes: StateFlow<List<InNote>> = _allTrashedNotes.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByPriority = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByPriority: StateFlow<List<InNote>> = _allNotesByPriority.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allRemindingNotes = MutableStateFlow<List<InNote>>(emptyList())
    val allRemindingNotes: StateFlow<List<InNote>> = _allRemindingNotes.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        screenModelScope.apply {
            launch(Dispatchers.IO) {
                getAllById.invoke().collect { notes -> _allNotesById.value = mapper.fromDomain(notes) }
            }
            launch(Dispatchers.IO) {
                getAllByName.invoke().collect { notes -> _allNotesByName.value = mapper.fromDomain(notes) }
            }
            launch(Dispatchers.IO) {
                getAllByNewest.invoke().collect { notes -> _allNotesByNewest.value = mapper.fromDomain(notes) }
            }
            launch(Dispatchers.IO) {
                getAllByOldest.invoke().collect { notes -> _allNotesByOldest.value = mapper.fromDomain(notes) }
            }
            launch(Dispatchers.IO) {
                getAllRemoved.invoke().collect { notes -> _allTrashedNotes.value = mapper.fromDomain(notes) }
            }
            launch(Dispatchers.IO) {
                getAllByPriority.invoke().collect { notes -> _allNotesByPriority.value = mapper.fromDomain(notes) }
            }
            launch(Dispatchers.IO) {
                getAllReminding.invoke().collect { notes -> _allRemindingNotes.value = mapper.fromDomain(notes) }
            }
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

    fun updateExpandedSortMenu(isShow: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(expandedSortMenu = isShow)
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

    fun updateErasing(isErase: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isErase = isErase)
        }
    }

    fun updateScreen(isHome: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isHomeScreen = isHome)
        }
    }

    fun updateOptionsDialog(isShow: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isOptionsDialog = isShow)
        }
    }

    fun updateSelectedNote(data: Data?) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedNote = data)
        }
    }
}