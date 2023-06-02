package com.example.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.NoteUseCase
import com.example.note.mapper.NoteMapper
import com.example.note.model.Note as InNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllById: NoteUseCase.GetAllNotesById,
    private val getAllByOldest: NoteUseCase.GetAllNotesByOldest,
    private val getAllByNewest: NoteUseCase.GetAllNotesByNewest,
    private val getAllByName: NoteUseCase.GetAllNotesByName,
    private val getAllTrashed: NoteUseCase.GetAllTrashedNotes,
    private val getAllByPriority: NoteUseCase.GetAllNotesByPriority,
    private val getAllReminding: NoteUseCase.GetAllRemindingNotes,
    private val mapper: NoteMapper
): ViewModel() {

    var isProcessing by mutableStateOf(false)
        private set

    // for observing the dataEntity changes.
    private val _allNotesById = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesById: StateFlow<List<InNote>>
        get() = _allNotesById.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByOldest = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByOldest : StateFlow<List<InNote>>
        get() = _allNotesByOldest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByNewest = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByNewest : StateFlow<List<InNote>>
        get() = _allNotesByNewest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByName = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByName : StateFlow<List<InNote>>
        get() = _allNotesByName.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allTrashedNotes = MutableStateFlow<List<InNote>>(emptyList())
    val allTrashedNotes : StateFlow<List<InNote>>
        get() = _allTrashedNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByPriority = MutableStateFlow<List<InNote>>(emptyList())
    val allNotesByPriority : StateFlow<List<InNote>>
        get() = _allNotesByPriority.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allRemindingNotes = MutableStateFlow<List<InNote>>(emptyList())
    val allRemindingNotes : StateFlow<List<InNote>>
        get() = _allRemindingNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.apply {
            isProcessing = true
            launch(Dispatchers.IO) {
                getAllById.invoke().collect { list ->
                    _allNotesById.value = list.map { note -> mapper.toView(note) }
                }
            }
            launch(Dispatchers.IO) {
                getAllByName.invoke().collect { list ->
                    _allNotesByName.value = list.map { note -> mapper.toView(note) }
                }
            }
            launch(Dispatchers.IO) {
                getAllByNewest.invoke().collect { list ->
                    _allNotesByNewest.value = list.map { note -> mapper.toView(note) }
                }
            }
            launch(Dispatchers.IO) {
                getAllByOldest.invoke().collect { list ->
                    _allNotesByOldest.value = list.map { note -> mapper.toView(note) }
                }
            }
            launch(Dispatchers.IO) {
                getAllTrashed.invoke().collect { list ->
                    _allTrashedNotes.value = list.map { note -> mapper.toView(note) }
                }
            }
            launch(Dispatchers.IO) {
                getAllByPriority.invoke().collect { list ->
                    _allNotesByPriority.value = list.map { note -> mapper.toView(note) }
                }
            }
            launch(Dispatchers.IO) {
                getAllReminding.invoke().collect { list ->
                    _allRemindingNotes.value = list.map { note -> mapper.toView(note) }
                }
            }
            isProcessing = false
        }
    }

}