package com.example.domain.repos

import com.example.local.model.NoteAndLabel
import kotlinx.coroutines.flow.Flow

interface NoteAndLabelRepo {

    val getAllNotesAndLabels: Flow<List<NoteAndLabel>>

    suspend fun addNoteAndLabel(noteAndLabel: NoteAndLabel)

    suspend fun deleteNoteAndLabel(noteAndLabel: NoteAndLabel)
}