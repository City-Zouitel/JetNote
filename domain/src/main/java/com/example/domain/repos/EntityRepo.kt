package com.example.domain.repos

import com.example.local.model.relational.Entity
import kotlinx.coroutines.flow.Flow

interface EntityRepo {

    val getAllNotesById: Flow<List<Entity>>

    val getAllNotesByName: Flow<List<Entity>>

    val getAllNotesByNewest: Flow<List<Entity>>

    val getAllNotesByOldest: Flow<List<Entity>>

    val getAllTrashedNotes: Flow<List<Entity>>

    val allNotesByPriority: Flow<List<Entity>>

    val getAllRemindingNotes: Flow<List<Entity>>

}