package com.example.jetnote.reposImp

import com.example.jetnote.repos.NoteAndLabelRepo
import com.example.local.db.daos.NoteAndLabelDao
import com.example.local.db.entities.note_and_label.NoteAndLabel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class NoteAndLabelRepoImp @Inject constructor(
    private val dao: NoteAndLabelDao
): NoteAndLabelRepo{

    override val getAllNotesAndLabels: Flow<List<NoteAndLabel>>
        get() = dao.getAllNotesAndLabels()

    override suspend fun addNoteAndLabel(noteAndLabel: NoteAndLabel) {
        coroutineScope { launch { dao.addNoteAndLabel(noteAndLabel) } }
    }

    override suspend fun deleteNoteAndLabel(noteAndLabel: NoteAndLabel) {
        coroutineScope { launch { dao.deleteNoteAndLabel(noteAndLabel) } }
    }
}