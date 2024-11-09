package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.ReminderRepo
import city.zouitel.repository.datasource.ReminderDataSource
import city.zouitel.repository.mapper.ReminderMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Reminder as OutReminder

class ReminderRepoImpl(
    private val dataSource: ReminderDataSource,
    private val mapper: ReminderMapper
): ReminderRepo {

    override val observeAllReminders: Flow<List<OutReminder>>
        get() = dataSource.observeAllReminders.map { reminders -> mapper.toDomain(reminders) }

    override suspend fun observeRemindersById(id: Long): Flow<List<OutReminder>> {
        return dataSource.observeRemindersById(id).map { reminders -> mapper.toDomain(reminders) }
    }

    override suspend fun insertReminder(reminder: OutReminder) {
        dataSource.insertReminder(mapper.fromDomain(reminder))
    }

    override suspend fun updateReminder(reminder: OutReminder) {
        dataSource.updateReminder(mapper.fromDomain(reminder))
    }

    override suspend fun deleteReminderById(id: Long) {
        dataSource.deleteReminderById(id)
    }

    override suspend fun deleteAllReminders() {
        dataSource.deleteAllReminders()
    }
}