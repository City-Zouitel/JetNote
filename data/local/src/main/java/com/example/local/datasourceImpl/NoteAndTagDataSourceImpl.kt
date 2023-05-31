package com.example.local.datasourceImpl

import com.example.local.dao.NoteAndTagDao
import com.example.local.mapper.NoteAndTagMapper
import com.example.repository.datasource.NoteAndTagDataSource
import com.example.repository.model.NoteAndTag as OutNoteAndTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndTagDataSourceImpl @Inject constructor(
    private val noteAndTagDao: NoteAndTagDao,
    private val noteAndTagMapper: NoteAndTagMapper
): NoteAndTagDataSource {
    override val getAllNotesAndTags: Flow<List<OutNoteAndTag>>
        get() = noteAndTagDao.getAllNotesAndTags().map { list ->
            list.map {
                noteAndTagMapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag) {
        noteAndTagDao.addNoteAndTag(noteAndTagMapper.toLocal(noteAndTag))
    }

    override suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag) {
        noteAndTagDao.deleteNoteAndTag(noteAndTagMapper.toLocal(noteAndTag))
    }
}