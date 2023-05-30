package com.example.local.datasourceImpl

import com.example.local.dao.NoteAndLinkDao
import com.example.local.mapper.NoteAndLinkMapper
import com.example.repository.datasource.NoteAndLinkDataSource
import com.example.repository.model.NoteAndLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndLinkDataSourceImpl @Inject constructor(
    private val noteAndLinkDao: NoteAndLinkDao,
    private val  noteAndLinkMapper: NoteAndLinkMapper
): NoteAndLinkDataSource {
    override val getAllNotesAndLinks: Flow<List<NoteAndLink>>
        get() = noteAndLinkDao.getAllNotesAndLinks().map { list ->
            list.map {
                noteAndLinkMapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndLink(noteAndLink: NoteAndLink) {
        noteAndLinkDao.addNoteAndLink(noteAndLinkMapper.toLocal(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: NoteAndLink) {
        noteAndLinkDao.deleteNoteAndLink(noteAndLinkMapper.toLocal(noteAndLink))
    }
}