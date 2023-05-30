package com.example.repository.datasource

import com.example.repository.model.NoteAndTask
import kotlinx.coroutines.flow.Flow

interface NoteAndTaskDataSource {

    val getAllNotesAndTask: Flow<List<NoteAndTask>>

    suspend fun addNoteAndTask(noteAndTask: NoteAndTask)

    suspend fun deleteNoteAndTask(noteAndTask: NoteAndTask)
}