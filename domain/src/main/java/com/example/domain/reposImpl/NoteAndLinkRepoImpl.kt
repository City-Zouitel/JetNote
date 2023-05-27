package com.example.domain.reposImpl

import com.example.domain.repos.NoteAndLinkRepo
import com.example.local.dao.NoteAndLinkDao
import com.example.local.model.NoteAndLink
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

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