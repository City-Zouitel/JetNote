package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.NoteAndTaskUseCase
import com.example.tasks.mapper.NoteAndTaskMapper
import com.example.tasks.model.NoteAndTask as InNoteAndTask
import com.example.domain.model.NoteAndTask as OutNoteAndTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAndTaskViewModel @Inject constructor(
    private val getAll: NoteAndTaskUseCase.GetAllNotesAndTask,
    private val add: NoteAndTaskUseCase.AddNoteAndTask,
    private val delete: NoteAndTaskUseCase.DeleteNoteAndTask,
    private val mapper: NoteAndTaskMapper
): ViewModel() {

    private val _getAllNotesAndTodo = MutableStateFlow<List<OutNoteAndTask>>(emptyList())
    val getAllNotesAndTodo:StateFlow<List<OutNoteAndTask>>
    get() = _getAllNotesAndTodo.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch {
            getAll.invoke().collect {
                _getAllNotesAndTodo.value = it
            }
        }
    }

    fun addNoteAndTodoItem(noteAndTodo: InNoteAndTask) = viewModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(noteAndTodo))
    }
    fun deleteNoteAndTodoItem(noteAndTodo: InNoteAndTask) = viewModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(noteAndTodo))
    }
}