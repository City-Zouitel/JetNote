package com.example.local.daos

import androidx.room.*
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndLinkDao {

    @Query("select * from note_and_link")
    fun getAllNotesAndLinks(): Flow<List<NoteAndLink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndLink(noteAndLink: NoteAndLink)

    @Delete
    suspend fun deleteNoteAndLink(noteAndLink: NoteAndLink)
}