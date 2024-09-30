package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Note as OutNote
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.mapper.WidgetMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dataSource: NoteDataSource,
    private val mapper: WidgetMapper
): NoteRepository {
    override val getAllNotesById: Flow<List<OutNote>>
        get() = dataSource.getAllNotesById.map { notes -> mapper.toDomain(notes) }

    override val getAllNotesByName: Flow<List<OutNote>>
        get() = dataSource.getAllNotesByName.map { notes -> mapper.toDomain(notes) }

    override val getAllNotesByNewest: Flow<List<OutNote>>
        get() = dataSource.getAllNotesByNewest.map { notes -> mapper.toDomain(notes) }

    override val getAllNotesByOldest: Flow<List<OutNote>>
        get() = dataSource.getAllNotesByOldest.map { notes -> mapper.toDomain(notes) }

    override val getAllRemovedNotes: Flow<List<OutNote>>
        get() = dataSource.getAllTrashedNotes.map { notes -> mapper.toDomain(notes) }

    override val allNotesByPriority: Flow<List<OutNote>>
        get() = dataSource.allNotesByPriority.map{ notes -> mapper.toDomain(notes) }

    override val getAllRemindingNotes: Flow<List<OutNote>>
        get() = dataSource.getAllRemindingNotes.map { notes -> mapper.toDomain(notes) }
}