package com.example.local.dao

import androidx.room.*
import com.example.local.model.DataEntity

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(data: DataEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editNote(data: DataEntity)

    @Delete
    suspend fun deleteNote(data: DataEntity)

    // delete all trashed notes.
    @Query("delete from NOTES_TABLE where Trashed = 1")
    suspend fun deleteAllTrashedNotes()

}