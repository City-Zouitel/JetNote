package com.example.repository.datasource

import com.example.repository.model.NoteAndLink
import kotlinx.coroutines.flow.Flow

interface NoteAndLinkDataSource {

    val getAllNotesAndLinks: Flow<List<NoteAndLink>>

    suspend fun addNoteAndLink(noteAndLink: NoteAndLink)

    suspend fun deleteNoteAndLink(noteAndLink: NoteAndLink)
}