package com.example.domain.repository

import com.example.domain.model.NoteAndLink as  OutNoteAndLink
import kotlinx.coroutines.flow.Flow

interface NoteAndLinkRepository {

    val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>

    suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink)

    suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink)
}