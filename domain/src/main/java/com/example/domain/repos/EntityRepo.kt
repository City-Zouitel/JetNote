package com.example.domain.repos

import com.example.local.model.relational.MainEntity
import kotlinx.coroutines.flow.Flow

interface EntityRepo {

    val getAllNotesById: Flow<List<MainEntity>>

    val getAllNotesByName: Flow<List<MainEntity>>

    val getAllNotesByNewest: Flow<List<MainEntity>>

    val getAllNotesByOldest: Flow<List<MainEntity>>

    val getAllTrashedNotes: Flow<List<MainEntity>>

    val allNotesByPriority: Flow<List<MainEntity>>

    val getAllRemindingNotes: Flow<List<MainEntity>>

}