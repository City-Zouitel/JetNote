package city.zouitel.screens.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.note.mapper.NoteMapper
import city.zouitel.note.model.Data
import city.zouitel.screens.state.MainUiState
import city.zouitel.tags.model.Tag
import city.zouitel.note.model.Note as InNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
class MainScreenModel(
    private val getAllById: NoteUseCase.GetAllNotesById,
    private val getAllByOldest: NoteUseCase.GetAllNotesByOldest,
    private val getAllByNewest: NoteUseCase.GetAllNotesByNewest,
    private val getAllByName: NoteUseCase.GetAllNotesByName,
    private val getAllTrashed: NoteUseCase.GetAllTrashedNotes,
    private val getAllByPriority: NoteUseCase.GetAllNotesByPriority,
    private val getAllReminding: NoteUseCase.GetAllRemindingNotes,
    private val mapper: NoteMapper
): ScreenModel {

    internal var uiState: MainUiState by mutableStateOf(MainUiState())
        private set

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
                getAllById.invoke().collect { list ->
                    _allNotesById.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
            launch(Dispatchers.IO) {
                getAllByName.invoke().collect { list ->
                    _allNotesByName.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
            launch(Dispatchers.IO) {
                getAllByNewest.invoke().collect { list ->
                    _allNotesByNewest.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
            launch(Dispatchers.IO) {
                getAllByOldest.invoke().collect { list ->
                    _allNotesByOldest.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
            launch(Dispatchers.IO) {
                getAllTrashed.invoke().collect { list ->
                    _allTrashedNotes.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
            launch(Dispatchers.IO) {
                getAllByPriority.invoke().collect { list ->
                    _allNotesByPriority.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
            launch(Dispatchers.IO) {
                getAllReminding.invoke().collect { list ->
                    _allRemindingNotes.value = list.map { note -> mapper.toView(note) }
                }
                uiState = uiState.copy(isProcessing = isActive)
            }
        }
    }

    fun updateSearchTitle(title: String) {
        screenModelScope.launch {
            uiState = uiState.copy(searchTitle = title)
        }
    }

    fun updateSearchTag(tag: Tag?) {
        screenModelScope.launch {
            uiState = uiState.copy(searchTag = tag)
        }
    }

    fun updateExpandedSortMenu(isShow: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(expandedSortMenu = isShow)
        }
    }

    fun updateSelection(value: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(isSelection = value)
        }
    }

    fun updateSelectedNotes(note: Data) {
        screenModelScope.launch {
            uiState = uiState.copy().addSelectedNote(note)
        }
    }

    fun clearSelectionNotes() {
        screenModelScope.launch {
            uiState = uiState.copy(selectedNotes = mutableStateListOf())
        }
    }

    fun updateErasing(isErase: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(isErase = isErase)
        }
    }

    fun updateScreen(isHome: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(isHomeScreen = isHome)
        }
    }
}