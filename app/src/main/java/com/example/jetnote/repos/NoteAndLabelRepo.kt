package com.example.jetnote.repos

import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import kotlinx.coroutines.flow.Flow

interface NoteAndLabelRepo {

    val getAllNotesAndLabels: Flow<List<NoteAndLabel>>

    suspend fun addNoteAndLabel(noteAndLabel: NoteAndLabel)

    suspend fun deleteNoteAndLabel(noteAndLabel: NoteAndLabel)
}