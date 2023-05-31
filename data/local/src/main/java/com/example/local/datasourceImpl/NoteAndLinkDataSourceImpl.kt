package com.example.local.datasourceImpl

import com.example.local.dao.NoteAndLinkDao
import com.example.local.mapper.NoteAndLinkMapper
import com.example.repository.datasource.NoteAndLinkDataSource
import com.example.repository.model.NoteAndLink as OutNoteAndLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndLinkDataSourceImpl @Inject constructor(
    private val noteAndLinkDao: NoteAndLinkDao,
    private val  noteAndLinkMapper: NoteAndLinkMapper
): NoteAndLinkDataSource {
    override val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>
        get() = noteAndLinkDao.getAllNotesAndLinks().map { list ->
            list.map {
                noteAndLinkMapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink) {
        noteAndLinkDao.addNoteAndLink(noteAndLinkMapper.toLocal(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink) {
        noteAndLinkDao.deleteNoteAndLink(noteAndLinkMapper.toLocal(noteAndLink))
    }
}