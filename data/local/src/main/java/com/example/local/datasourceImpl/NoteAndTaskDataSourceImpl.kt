package com.example.local.datasourceImpl

import com.example.local.dao.NoteAndTaskDao
import com.example.local.mapper.NoteAndTaskMapper
import com.example.repository.datasource.NoteAndTaskDataSource
import com.example.repository.model.NoteAndTask as OutNoteAndTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndTaskDataSourceImpl @Inject constructor(
    private val noteAndTaskDao: NoteAndTaskDao,
    private val noteAndTaskMapper: NoteAndTaskMapper
): NoteAndTaskDataSource {
    override val getAllNotesAndTask: Flow<List<OutNoteAndTask>>
        get() = noteAndTaskDao.getAllNoteAndTasks().map { list ->
            list.map {
                noteAndTaskMapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask) {
        noteAndTaskDao.addNoteAndTask(noteAndTaskMapper.toLocal(noteAndTask))
    }

    override suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask) {
        noteAndTaskDao.deleteNoteAndTask(noteAndTaskMapper.toLocal(noteAndTask))
    }
}