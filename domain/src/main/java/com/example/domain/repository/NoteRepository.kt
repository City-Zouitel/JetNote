package com.example.domain.repository

import com.example.domain.model.Note as OutNote
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    val getAllNotesById: Flow<List<OutNote>>

    val getAllNotesByName: Flow<List<OutNote>>

    val getAllNotesByNewest: Flow<List<OutNote>>

    val getAllNotesByOldest: Flow<List<OutNote>>

    val getAllTrashedNotes: Flow<List<OutNote>>

    val allNotesByPriority: Flow<List<OutNote>>

    val getAllRemindingNotes: Flow<List<OutNote>>

    val getAllUnVerifiedNotes: Flow<List<OutNote>>

}