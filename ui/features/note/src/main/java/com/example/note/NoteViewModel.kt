package com.example.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.NoteUseCase
import com.example.local.model.relational.NoteEntity
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
    private val getAllReminding: NoteUseCase.GetAllRemindingNotes
): ViewModel() {

    var isProcessing by mutableStateOf(false)
        private set

    // for observing the dataEntity changes.
    private val _allNotesById = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allNotesById: StateFlow<List<NoteEntity>>
        get() = _allNotesById.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByOldest = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allNotesByOldest : StateFlow<List<NoteEntity>>
        get() = _allNotesByOldest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByNewest = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allNotesByNewest : StateFlow<List<NoteEntity>>
        get() = _allNotesByNewest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByName = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allNotesByName : StateFlow<List<NoteEntity>>
        get() = _allNotesByName.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allTrashedNotes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allTrashedNotes : StateFlow<List<NoteEntity>>
        get() = _allTrashedNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByPriority = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allNotesByPriority : StateFlow<List<NoteEntity>>
        get() = _allNotesByPriority.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allRemindingNotes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val allRemindingNotes : StateFlow<List<NoteEntity>>
        get() = _allRemindingNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.apply {
            isProcessing = true
            launch(Dispatchers.IO) {
                getAllById.invoke().collect {
//                    _allNotesById.value = it
                }
            }
            launch(Dispatchers.IO) {
                getAllByName.invoke().collect {
//                    _allNotesByName.value = it
                }
            }
            launch(Dispatchers.IO) {
                getAllByNewest.invoke().collect {
//                    _allNotesByNewest.value = it
                }
            }
            launch(Dispatchers.IO) {
                getAllByOldest.invoke().collect {
//                    _allNotesByOldest.value = it
                }
            }
            launch(Dispatchers.IO) {
                getAllTrashed.invoke().collect {
//                    _allTrashedNotes.value = it
                }
            }
            launch(Dispatchers.IO) {
                getAllByPriority.invoke().collect {
//                    _allNotesByPriority.value = it
                }
            }
            launch(Dispatchers.IO) {
                getAllReminding.invoke().collect {
//                    _allRemindingNotes.value = it
                }
            }
            isProcessing = false
        }
    }

}