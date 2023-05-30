package com.example.local.datasourceImpl

import com.example.local.dao.NoteDao
import com.example.local.mapper.NoteMapper
import com.example.repository.datasource.NoteDataSource
import com.example.repository.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val noteMapper: NoteMapper
): NoteDataSource {

    override val getAllNotesById: Flow<List<Note>>
        get() = noteDao.getAllNotesById().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllNotesByName: Flow<List<Note>>
        get() = noteDao.getAllNotesByName().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllNotesByNewest: Flow<List<Note>>
        get() = noteDao.getAllNotesByNewest().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllNotesByOldest: Flow<List<Note>>
        get() = noteDao.getAllNotesByOldest().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllTrashedNotes: Flow<List<Note>>
        get() = noteDao.getAllTrashedNotes().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val allNotesByPriority: Flow<List<Note>>
        get() = noteDao.getAllNotesByPriority().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }

    override val getAllRemindingNotes: Flow<List<Note>>
        get() = noteDao.getAllRemindingNotes().map { list ->
            list.map {
                noteMapper.readOnly(it)
            }
        }
}