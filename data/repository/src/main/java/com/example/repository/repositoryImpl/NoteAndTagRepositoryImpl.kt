package com.example.repository.repositoryImpl

import com.example.domain.model.NoteAndTag as OutNoteAndTag
import com.example.domain.repository.NoteAndTagRepository
import com.example.repository.datasource.NoteAndTagDataSource
import com.example.repository.mapper.NoteAndTagMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndTagRepositoryImpl @Inject constructor(
    private val dataSource: NoteAndTagDataSource,
    private val mapper: NoteAndTagMapper
): NoteAndTagRepository {
    override val getAllNotesAndTags: Flow<List<OutNoteAndTag>>
        get() = dataSource.getAllNotesAndTags.map { list ->
            list.map { noteAndTag ->
                mapper.readOnly(noteAndTag)
            }
        }

    override suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag) {
        dataSource.addNoteAndTag(mapper.toRepository(noteAndTag))
    }

    override suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag) {
        dataSource.deleteNoteAndTag(mapper.toRepository(noteAndTag))
    }
}