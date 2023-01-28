package com.example.domain.repos

import com.example.local.model.NoteAndTodo
import kotlinx.coroutines.flow.Flow

interface NoteAndTodoRepo {

    val getAllNotesAndTodo: Flow<List<NoteAndTodo>>

    suspend fun addNoteAndTodo(noteAndTodo: NoteAndTodo)

    suspend fun deleteNoteAndTodo(noteAndTodo: NoteAndTodo)
}