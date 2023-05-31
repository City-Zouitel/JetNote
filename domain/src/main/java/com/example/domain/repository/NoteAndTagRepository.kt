package com.example.domain.repository

import com.example.domain.model.NoteAndTag as OutNoteAndTag
import kotlinx.coroutines.flow.Flow

interface NoteAndTagRepository {

    val getAllNotesAndTags: Flow<List<OutNoteAndTag>>

    suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag)

    suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag)
}