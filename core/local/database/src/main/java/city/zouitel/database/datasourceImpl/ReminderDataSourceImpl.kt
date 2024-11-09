package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.ReminderDao
import city.zouitel.database.mapper.ReminderMapper
import city.zouitel.repository.datasource.ReminderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Reminder as OutReminder

class ReminderDataSourceImpl(
    private val dao: ReminderDao,
    private val mapper: ReminderMapper
): ReminderDataSource {

    override val observeAllReminders: Flow<List<OutReminder>>
        get() = dao.observeAllReminders.map { mapper.toRepo(it) }

    override suspend fun observeRemindersById(id: Long): Flow<List<OutReminder>> {
        return dao.observeAllReminderById(id).map { mapper.toRepo(it) }
    }

    override suspend fun insertReminder(reminder: OutReminder) {
        dao.insertReminder(mapper.fromRepo(reminder))
    }

    override suspend fun updateReminder(reminder: OutReminder) {
        dao.updateReminder(mapper.fromRepo(reminder))
    }

    override suspend fun deleteReminderById(id: Long) {
        dao.deleteReminderById(id)
    }

    override suspend fun deleteAllReminders() {
        dao.deleteAllReminders()
    }
}