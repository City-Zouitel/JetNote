package com.example.jetnote.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.db.entities.Entity
import com.example.jetnote.reposImp.EntityRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EntityVM @Inject constructor(
    private val repo: EntityRepoImp
): ViewModel() {

    var isProcessing by mutableStateOf(false)
        private set

    // for observing the note changes.
    private val _allNotesById = MutableStateFlow<List<Entity>>(emptyList())
    val allNotesById: StateFlow<List<Entity>>
        get() = _allNotesById.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByOldest = MutableStateFlow<List<Entity>>(emptyList())
    val allNotesByOldest : StateFlow<List<Entity>>
        get() = _allNotesByOldest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByNewest = MutableStateFlow<List<Entity>>(emptyList())
    val allNotesByNewest : StateFlow<List<Entity>>
        get() = _allNotesByNewest.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByName = MutableStateFlow<List<Entity>>(emptyList())
    val allNotesByName : StateFlow<List<Entity>>
        get() = _allNotesByName.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allTrashedNotes = MutableStateFlow<List<Entity>>(emptyList())
    val allTrashedNotes : StateFlow<List<Entity>>
        get() = _allTrashedNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allNotesByPriority = MutableStateFlow<List<Entity>>(emptyList())
    val allNotesByPriority : StateFlow<List<Entity>>
        get() = _allNotesByPriority.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _allRemindingNotes = MutableStateFlow<List<Entity>>(emptyList())
    val allRemindingNotes : StateFlow<List<Entity>>
        get() = _allRemindingNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.apply {
            isProcessing = true
            launch(Dispatchers.IO) {
                repo.getAllNotesById.collect {
                    _allNotesById.value = it
                }
            }
            launch(Dispatchers.IO) {
                repo.getAllNotesByName.collect {
                    _allNotesByName.value = it
                }
            }
            launch(Dispatchers.IO) {
                repo.getAllNotesByNewest.collect {
                    _allNotesByNewest.value = it
                }
            }
            launch(Dispatchers.IO) {
                repo.getAllNotesByOldest.collect {
                    _allNotesByOldest.value = it
                }
            }
            launch(Dispatchers.IO) {
                repo.getAllTrashedNotes.collect {
                    _allTrashedNotes.value = it
                }
            }
            launch(Dispatchers.IO) {
                repo.allNotesByPriority.collect {
                    _allNotesByPriority.value = it
                }
            }
            launch(Dispatchers.IO) {
                repo.getAllRemindingNotes.collect {
                    _allRemindingNotes.value = it
                }
            }
            isProcessing = false
        }
    }

}