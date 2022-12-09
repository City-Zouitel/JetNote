package com.example.jetnote.reposImp

import com.example.jetnote.db.daos.NotesDao
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.repos.NoteRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class NoteRepoImp @Inject constructor(
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