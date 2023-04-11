package com.example.domain.repos

import androidx.room.Dao
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import kotlinx.coroutines.flow.Flow

interface NoteAndLinkRepo {

    val getAllNotesAndLinks: Flow<List<NoteAndLink>>

    suspend fun addNoteAndLink(noteAndLink: NoteAndLink)

    suspend fun deleteNoteAndLink(noteAndLink: NoteAndLink)
}