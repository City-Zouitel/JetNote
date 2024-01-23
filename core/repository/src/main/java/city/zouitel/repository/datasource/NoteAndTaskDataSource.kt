package city.zouitel.repository.datasource

import city.zouitel.repository.model.NoteAndTask
import kotlinx.coroutines.flow.Flow

interface NoteAndTaskDataSource {

    val getAllNotesAndTask: Flow<List<NoteAndTask>>

    suspend fun addNoteAndTask(noteAndTask: NoteAndTask)

    suspend fun deleteNoteAndTask(noteAndTask: NoteAndTask)
}