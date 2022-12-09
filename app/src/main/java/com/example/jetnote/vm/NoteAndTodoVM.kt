package com.example.jetnote.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.db.entities.note_and_todo.NoteAndTodo
import com.example.jetnote.reposImp.NoteAndTodoRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAndTodoVM @Inject constructor(
    private val repo: NoteAndTodoRepoImp
): ViewModel() {

    private val _getAllNotesAndTodo = MutableStateFlow<List<NoteAndTodo>>(emptyList())
    val getAllNotesAndTodo:StateFlow<List<NoteAndTodo>>
    get() = _getAllNotesAndTodo.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch {
            repo.getAllNotesAndTodo.collect {
                _getAllNotesAndTodo.value = it
            }
        }
    }

    fun addNoteAndTodoItem(noteAndTodo: NoteAndTodo) = viewModelScope.launch(Dispatchers.IO) {
        repo.addNoteAndTodo(noteAndTodo)
    }
    fun deleteNoteAndTodoItem(noteAndTodo: NoteAndTodo) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteNoteAndTodo(noteAndTodo)
    }
}