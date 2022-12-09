package com.example.jetnote.repos

import com.example.jetnote.db.entities.note_and_todo.NoteAndTodo
import kotlinx.coroutines.flow.Flow

interface NoteAndTodoRepo {

    val getAllNotesAndTodo: Flow<List<NoteAndTodo>>

    suspend fun addNoteAndTodo(noteAndTodo: NoteAndTodo)

    suspend fun deleteNoteAndTodo(noteAndTodo: NoteAndTodo)
}