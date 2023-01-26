package com.example.local.db.daos

import androidx.room.*
import com.example.local.db.entities.note_and_todo.NoteAndTodo
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndTodoDao {

    @Query("select * from note_and_todo")
    fun getAllNotesAndTodo(): Flow<List<NoteAndTodo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndTodo(noteAndTodo: NoteAndTodo)

    @Delete
    suspend fun deleteNoteAndTodo(noteAndTodo: NoteAndTodo)
}