package com.example.domain.reposImpl

import com.example.domain.repos.NoteAndLinkRepo
import com.example.local.daos.NoteAndLinkDao
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class NoteAndLinkRepoImpl @Inject constructor(
    private val noteAndLinkDao: NoteAndLinkDao
): NoteAndLinkRepo {
    override val getAllNotesAndLinks: Flow<List<NoteAndLink>>
        get() = noteAndLinkDao.getAllNotesAndLinks()

    override suspend fun addNoteAndLink(noteAndLink: NoteAndLink) {
        coroutineScope { launch { noteAndLinkDao.addNoteAndLink(noteAndLink) } }
    }

    override suspend fun deleteNoteAndLink(noteAndLink: NoteAndLink) {
        coroutineScope { launch { noteAndLinkDao.deleteNoteAndLink(noteAndLink) } }
    }
}