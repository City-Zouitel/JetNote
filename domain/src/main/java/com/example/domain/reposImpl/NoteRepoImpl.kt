package com.example.domain.reposImpl

import com.example.domain.repos.NoteRepo
import com.example.local.dao.NotesDao
import com.example.local.model.Note
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class NoteRepoImpl @Inject constructor(
    private val dao: NotesDao,
): NoteRepo {

    override suspend fun addNote(noteNote: Note) {
        coroutineScope { launch { dao.addNote(noteNote) } }
    }

    override suspend fun editNote(noteNote: Note) {
        coroutineScope { launch { dao.editNote(noteNote) } }
    }

    override suspend fun deleteNote(noteNote: Note) {
        coroutineScope { launch { dao.deleteNote(noteNote) } }
    }

    override suspend fun deleteAllTrashedNotes() {
        coroutineScope { launch { dao.deleteAllTrashedNotes() } }
    }

}