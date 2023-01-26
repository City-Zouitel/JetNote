package com.example.local.db.daos

import androidx.room.*
import com.example.local.db.entities.note.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    // delete all trashed notes.
    @Query("delete from NOTES where Trashed = 1")
    suspend fun deleteAllTrashedNotes()

}