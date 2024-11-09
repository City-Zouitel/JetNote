package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndReminder
import kotlinx.coroutines.flow.Flow

interface NoteAndReminderRepo {

    val observeAllNotesAndReminders: Flow<List<NoteAndReminder>>

    suspend fun observeNoteAndRemindersById(noteId: String): Flow<List<NoteAndReminder>>

    suspend fun insertNoteAndReminder(noteAndReminder: NoteAndReminder)

    suspend fun updateNoteAndReminder(noteAndReminder: NoteAndReminder)

    suspend fun deleteNoteAndReminderById(noteId: String)

    suspend fun deleteAllNotesAndReminders()
}