package com.example.repository.repositoryImpl

import com.example.domain.model.NoteAndTask as OutNoteAndTask
import com.example.domain.repository.NoteAndTaskRepository
import com.example.repository.datasource.NoteAndTaskDataSource
import com.example.repository.mapper.NoteAndTaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteAndTaskRepositoryImpl @Inject constructor(
    private val dataSource: NoteAndTaskDataSource,
    private val mapper: NoteAndTaskMapper
): NoteAndTaskRepository {
    override val getAllNotesAndTask: Flow<List<OutNoteAndTask>>
        get() = dataSource.getAllNotesAndTask.map { list ->
            list.map { noteAndTask ->
                mapper.readOnly(noteAndTask)
            }
        }

    override suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask) {
        dataSource.addNoteAndTask(mapper.toRepository(noteAndTask))
    }

    override suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask) {
        dataSource.deleteNoteAndTask(mapper.toRepository(noteAndTask))
    }
}