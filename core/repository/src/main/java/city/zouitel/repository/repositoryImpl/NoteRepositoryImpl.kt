package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Note as OutNote
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.mapper.NoteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl /*@Inject*/ constructor(
    private val dataSource: NoteDataSource,
    private val mapper: NoteMapper
): NoteRepository {
    override val getAllNotesById: Flow<List<OutNote>>
        get() = dataSource.getAllNotesById.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }

    override val getAllNotesByName: Flow<List<OutNote>>
        get() = dataSource.getAllNotesByName.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }

    override val getAllNotesByNewest: Flow<List<OutNote>>
        get() = dataSource.getAllNotesByNewest.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }

    override val getAllNotesByOldest: Flow<List<OutNote>>
        get() = dataSource.getAllNotesByOldest.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }

    override val getAllTrashedNotes: Flow<List<OutNote>>
        get() = dataSource.getAllTrashedNotes.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }

    override val allNotesByPriority: Flow<List<OutNote>>
        get() = dataSource.allNotesByPriority.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }

    override val getAllRemindingNotes: Flow<List<OutNote>>
        get() = dataSource.getAllRemindingNotes.map { list ->
            list.map {  note ->
                mapper.toDomain(note)
            }
        }
}