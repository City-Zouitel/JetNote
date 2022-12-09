package com.example.jetnote.repos

import com.example.jetnote.db.entities.Entity
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