package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask
import city.zouitel.domain.repository.NoteAndTaskRepository
import city.zouitel.repository.datasource.NoteAndTaskDataSource
import city.zouitel.repository.mapper.NoteAndTaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTaskRepositoryImpl(
    private val dataSource: NoteAndTaskDataSource,
    private val mapper: NoteAndTaskMapper
): NoteAndTaskRepository {
    override val getAllNotesAndTask: Flow<List<OutNoteAndTask>>
        get() = dataSource.getAllNotesAndTask.map { notesAndTask -> mapper.toDomain(notesAndTask) }

    override suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask) {
        dataSource.addNoteAndTask(mapper.fromDomain(noteAndTask))
    }

    override suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask) {
        dataSource.deleteNoteAndTask(mapper.fromDomain(noteAndTask))
    }
}