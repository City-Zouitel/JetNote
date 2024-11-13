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

    override suspend fun observeById(uid: String): Flow<List<OutReminder>> {
        return dataSource.observeById(uid).map { reminders -> mapper.toDomain(reminders) }
    }

    override suspend fun insert(reminder: OutReminder) {
        dataSource.insert(mapper.fromDomain(reminder))
    }

    override suspend fun update(reminder: OutReminder) {
        dataSource.update(mapper.fromDomain(reminder))
    }

    override suspend fun delete(id: Long) {
        dataSource.delete(id)
    }

    override suspend fun deleteAll() {
        dataSource.deleteAll()
    }
}