package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.NoteAndReminderRepo
import city.zouitel.repository.datasource.NoteAndReminderDataSource
import city.zouitel.repository.mapper.NoteAndReminderMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.NoteAndReminder as OutNoteAndReminder

class NoteAndReminderRepoImpl(
    private val dataSource: NoteAndReminderDataSource,
    private val mapper: NoteAndReminderMapper
): NoteAndReminderRepo {

    override val observeAllNotesAndReminders: Flow<List<OutNoteAndReminder>>
        get() = dataSource.observeAllNotesAndReminders.map { reminders -> mapper.toDomain(reminders) }

    override suspend fun observeNoteAndRemindersById(noteId: String): Flow<List<OutNoteAndReminder>> {
        return dataSource.observeNoteAndRemindersById(noteId).map { reminders -> mapper.toDomain(reminders) }
    }

    override suspend fun insertNoteAndReminder(noteAndReminder: OutNoteAndReminder) {
        dataSource.insertNoteAndReminder(mapper.fromDomain(noteAndReminder))
    }

    override suspend fun updateNoteAndReminder(noteAndReminder: OutNoteAndReminder) {
        dataSource.updateNoteAndReminder(mapper.fromDomain(noteAndReminder))
    }

    override suspend fun deleteNoteAndReminderById(noteId: String) {
        dataSource.deleteNoteAndReminderById(noteId)
    }

    override suspend fun deleteAllNotesAndReminders() {
        dataSource.deleteAllNotesAndReminders()
    }
}