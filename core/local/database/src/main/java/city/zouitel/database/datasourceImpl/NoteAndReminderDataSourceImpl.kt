package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndReminderDao
import city.zouitel.database.mapper.NoteAndReminderMapper
import city.zouitel.repository.datasource.NoteAndReminderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.NoteAndReminder as OutNoteAndReminder

class NoteAndReminderDataSourceImpl(
    private val dao: NoteAndReminderDao,
    private val mapper: NoteAndReminderMapper
): NoteAndReminderDataSource {

    override val observeAllNotesAndReminders: Flow<List<OutNoteAndReminder>>
        get() = dao.observeAllNotesAndReminders.map { mapper.toRepo(it) }

    override suspend fun observeNoteAndRemindersById(noteId: String): Flow<List<OutNoteAndReminder>> {
        return dao.observeNoteAndRemindersById(noteId).map { mapper.toRepo(it) }
    }

    override suspend fun insertNoteAndReminder(noteAndReminder: OutNoteAndReminder) {
        dao.insertNoteAndReminder(mapper.fromRepo(noteAndReminder))
    }

    override suspend fun updateNoteAndReminder(noteAndReminder: OutNoteAndReminder) {
        dao.updateNoteAndReminder(mapper.fromRepo(noteAndReminder))
    }

    override suspend fun deleteNoteAndReminderById(noteId: String) {
        dao.deleteNoteAndReminderById(noteId)
    }

    override suspend fun deleteAllNotesAndReminders() {
        dao.deleteAllNotesAndReminders()
    }
}