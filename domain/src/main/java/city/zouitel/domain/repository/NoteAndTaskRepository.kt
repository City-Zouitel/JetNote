package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask
import kotlinx.coroutines.flow.Flow

interface NoteAndTaskRepository {

    val getAllNotesAndTask: Flow<List<OutNoteAndTask>>

    suspend fun addNoteAndTask(noteAndTask: OutNoteAndTask)

    suspend fun deleteNoteAndTask(noteAndTask: OutNoteAndTask)
}