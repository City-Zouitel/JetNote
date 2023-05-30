package com.example.local.dao

import androidx.room.*
import com.example.local.model.NoteAndTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndTagDao {

    @Query("select * from note_and_label")
    fun getAllNotesAndTags():Flow<List<NoteAndTagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndTag(noteAndLabel: NoteAndTagEntity)

    @Delete
    suspend fun deleteNoteAndTag(noteAndLabel: NoteAndTagEntity)
}