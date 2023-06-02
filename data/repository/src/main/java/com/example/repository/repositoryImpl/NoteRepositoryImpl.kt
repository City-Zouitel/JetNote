package com.example.repository.repositoryImpl

import com.example.domain.model.Note as OutNote
import com.example.domain.repository.NoteRepository
import com.example.repository.datasource.NoteDataSource
import com.example.repository.mapper.NoteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
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