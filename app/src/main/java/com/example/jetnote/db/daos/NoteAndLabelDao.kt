package com.example.jetnote.db.daos

import androidx.room.*
import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndLabelDao {

    @Query("select * from note_and_label")
    fun getAllNotesAndLabels():Flow<List<NoteAndLabel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndLabel(noteAndLabel: NoteAndLabel)

    @Delete
    suspend fun deleteNoteAndLabel(noteAndLabel: NoteAndLabel)
}