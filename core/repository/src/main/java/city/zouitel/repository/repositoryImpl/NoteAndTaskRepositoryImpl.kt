package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask
import city.zouitel.domain.repository.NoteAndTaskRepository
import city.zouitel.repository.datasource.NoteAndTaskDataSource
import city.zouitel.repository.mapper.NoteAndTaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTaskRepositoryImpl /*@Inject*/ constructor(
    private val dataSource: NoteAndTaskDataSource,
    private val mapper: NoteAndTaskMapper
): NoteAndTaskRepository {
    override val getAllNotesAndTask: Flow<List<OutNoteAndTask>>
        get() = dataSource.getAllNotesAndTask.map { list ->
            list.map { noteAndTask ->
                mapper.toDomain(noteAndTask)
            }
        }

    override suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask) {
        dataSource.addNoteAndTask(mapper.toRepository(noteAndTask))
    }

    override suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask) {
        dataSource.deleteNoteAndTask(mapper.toRepository(noteAndTask))
    }
}