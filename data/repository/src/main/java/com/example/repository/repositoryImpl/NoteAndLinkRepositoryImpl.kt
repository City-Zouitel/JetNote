package com.example.repository.repositoryImpl

import com.example.domain.model.NoteAndLink as OutNoteAndLink
import com.example.domain.repository.NoteAndLinkRepository
import com.example.repository.datasource.NoteAndLinkDataSource
import com.example.repository.mapper.NoteAndLinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndLinkRepositoryImpl @Inject constructor(
    private val dataSource: NoteAndLinkDataSource,
    private val mapper: NoteAndLinkMapper
): NoteAndLinkRepository {
    override val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>
        get() = dataSource.getAllNotesAndLinks.map { list ->
            list.map { noteAndLink ->
                mapper.readOnly(noteAndLink)
            }
        }

    override suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink) {
        dataSource.addNoteAndLink(mapper.toRepository(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink) {
        dataSource.deleteNoteAndLink(mapper.toRepository(noteAndLink))
    }
}