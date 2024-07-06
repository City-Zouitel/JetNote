package city.zouitel.domain.repository

import city.zouitel.domain.model.Note as OutNote
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    val getAllNotesById: Flow<List<OutNote>>

    val getAllNotesByName: Flow<List<OutNote>>

    val getAllNotesByNewest: Flow<List<OutNote>>

    val getAllNotesByOldest: Flow<List<OutNote>>

    val getAllRemovedNotes: Flow<List<OutNote>>

    val allNotesByPriority: Flow<List<OutNote>>

    val getAllRemindingNotes: Flow<List<OutNote>>

}