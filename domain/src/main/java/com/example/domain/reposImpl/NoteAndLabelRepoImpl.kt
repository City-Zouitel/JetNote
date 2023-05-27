package com.example.domain.reposImpl

import com.example.domain.repos.NoteAndLabelRepo
import com.example.local.dao.NoteAndLabelDao
import com.example.local.model.NoteAndLabel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class NoteAndLabelRepoImpl @Inject constructor(
    private val dao: NoteAndLabelDao
): NoteAndLabelRepo {

    override val getAllNotesAndLabels: Flow<List<NoteAndLabel>>
        get() = dao.getAllNotesAndLabels()

    override suspend fun addNoteAndLabel(noteAndLabel: NoteAndLabel) {
        coroutineScope { launch { dao.addNoteAndLabel(noteAndLabel) } }
    }

    override suspend fun deleteNoteAndLabel(noteAndLabel: NoteAndLabel) {
        coroutineScope { launch { dao.deleteNoteAndLabel(noteAndLabel) } }
    }
}