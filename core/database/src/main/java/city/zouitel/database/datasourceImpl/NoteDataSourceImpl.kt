package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteDao
import city.zouitel.database.mapper.NoteMapper
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.model.Note as OutNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteDataSourceImpl /*@Inject*/ constructor(
    private val noteDao: NoteDao,
    private val noteMapper: NoteMapper
): NoteDataSource {

    override val getAllNotesById: Flow<List<OutNote>>
        get() = noteDao.getAllNotesById().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllNotesByName: Flow<List<OutNote>>
        get() = noteDao.getAllNotesByName().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllNotesByNewest: Flow<List<OutNote>>
        get() = noteDao.getAllNotesByNewest().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllNotesByOldest: Flow<List<OutNote>>
        get() = noteDao.getAllNotesByOldest().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllTrashedNotes: Flow<List<OutNote>>
        get() = noteDao.getAllTrashedNotes().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val allNotesByPriority: Flow<List<OutNote>>
        get() = noteDao.getAllNotesByPriority().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllRemindingNotes: Flow<List<OutNote>>
        get() = noteDao.getAllRemindingNotes().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }
}