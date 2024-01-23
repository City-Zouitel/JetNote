package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndTaskDao
import city.zouitel.database.mapper.NoteAndTaskMapper
import city.zouitel.repository.datasource.NoteAndTaskDataSource
import city.zouitel.repository.model.NoteAndTask as OutNoteAndTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTaskDataSourceImpl /*@Inject*/ constructor(
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