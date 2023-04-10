package com.example.domain.reposImpl

import com.example.domain.repos.NoteAndLinkRepo
import com.example.local.daos.NoteAndLinkDao
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@ViewModelScoped
class NoteAndLinkRepoImpl @Inject constructor(
    private val noteAndLinkDao: NoteAndLinkDao
): NoteAndLinkRepo {
    override val getAllNotesAndLinks: Flow<List<Link>>
        get() = emptyFlow()

    override suspend fun addNoteAndLink(noteAndLink: NoteAndLink) {
    }

    override suspend fun deleteNoteAndLink(noteAndLink: NoteAndLink) {
    }
}