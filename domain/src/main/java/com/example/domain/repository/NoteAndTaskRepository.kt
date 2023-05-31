package com.example.domain.repository

import com.example.domain.model.NoteAndTask as OutNoteAndTask
import kotlinx.coroutines.flow.Flow

interface NoteAndTaskRepository {

    val getAllNotesAndTask: Flow<List<OutNoteAndTask>>

    suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask)

    suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask)
}