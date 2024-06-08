package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndTaskDao
import city.zouitel.database.mapper.NoteAndTaskMapper
import city.zouitel.repository.datasource.NoteAndTaskDataSource
import city.zouitel.repository.model.NoteAndTask as OutNoteAndTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTaskDataSourceImpl(
    private val dao: NoteAndTaskDao,
    private val mapper: NoteAndTaskMapper
): NoteAndTaskDataSource {
    override val getAllNotesAndTask: Flow<List<OutNoteAndTask>>
        get() = dao.getAllNoteAndTasks().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask) {
        dao.addNoteAndTask(mapper.toLocal(noteAndTask))
    }

    override suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask) {
        dao.deleteNoteAndTask(mapper.toLocal(noteAndTask))
    }
}