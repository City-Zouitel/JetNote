package com.example.domain.reposImpl

import com.example.domain.repos.NoteAndTodoRepo
import com.example.local.daos.NoteAndTodoDao
import com.example.local.model.NoteAndTodo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class NoteAndTodoRepoImp @Inject constructor(
    private val dao: NoteAndTodoDao
): NoteAndTodoRepo {
    override val getAllNotesAndTodo: Flow<List<NoteAndTodo>>
        get() = dao.getAllNotesAndTodo()

    override suspend fun addNoteAndTodo(noteAndTodo: NoteAndTodo) {
        coroutineScope { launch { dao.addNoteAndTodo(noteAndTodo) } }
    }

    override suspend fun deleteNoteAndTodo(noteAndTodo: NoteAndTodo) {
        coroutineScope { launch { dao.deleteNoteAndTodo(noteAndTodo) } }
    }

}