package city.zouitel.repository.datasource

import city.zouitel.repository.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {

    val getAllNotesById: Flow<List<Note>>

    val getAllNotesByName: Flow<List<Note>>

    val getAllNotesByNewest: Flow<List<Note>>

    val getAllNotesByOldest: Flow<List<Note>>

    val getAllTrashedNotes: Flow<List<Note>>

    val allNotesByPriority: Flow<List<Note>>

    val getAllRemindingNotes: Flow<List<Note>>

}