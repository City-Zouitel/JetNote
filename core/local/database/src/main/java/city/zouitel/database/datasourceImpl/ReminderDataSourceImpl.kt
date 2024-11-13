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

    override suspend fun observeById(uid: String): Flow<List<OutReminder>> {
        return dao.observeAllById(uid).map { mapper.toRepo(it) }
    }

    override suspend fun insert(reminder: OutReminder) {
        dao.insert(mapper.fromRepo(reminder))
    }

    override suspend fun update(reminder: OutReminder) {
        dao.update(mapper.fromRepo(reminder))
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}