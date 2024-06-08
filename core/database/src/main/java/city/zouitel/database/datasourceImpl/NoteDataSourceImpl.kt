package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteDao
import city.zouitel.database.mapper.NoteMapper
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.model.Note as OutNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteDataSourceImpl(
    private val dao: NoteDao,
    private val mapper: NoteMapper
): NoteDataSource {

    override val getAllNotesById: Flow<List<OutNote>>
        get() = dao.getAllNotesById().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override val getAllNotesByName: Flow<List<OutNote>>
        get() = dao.getAllNotesByName().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override val getAllNotesByNewest: Flow<List<OutNote>>
        get() = dao.getAllNotesByNewest().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override val getAllNotesByOldest: Flow<List<OutNote>>
        get() = dao.getAllNotesByOldest().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override val getAllTrashedNotes: Flow<List<OutNote>>
        get() = dao.getAllTrashedNotes().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override val allNotesByPriority: Flow<List<OutNote>>
        get() = dao.getAllNotesByPriority().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override val getAllRemindingNotes: Flow<List<OutNote>>
        get() = dao.getAllRemindingNotes().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }
}