package city.zouitel.repository.datasource

import kotlinx.coroutines.flow.Flow
import city.zouitel.repository.model.NoteAndReminder as InNoteAndReminder

interface NoteAndReminderDataSource {

    val observeAllNotesAndReminders: Flow<List<InNoteAndReminder>>

    suspend fun observeNoteAndRemindersById(noteId: String): Flow<List<InNoteAndReminder>>

    suspend fun insertNoteAndReminder(noteAndReminder: InNoteAndReminder)

    suspend fun updateNoteAndReminder(noteAndReminder: InNoteAndReminder)

    suspend fun deleteNoteAndReminderById(noteId: String)

    suspend fun deleteAllNotesAndReminders()
}