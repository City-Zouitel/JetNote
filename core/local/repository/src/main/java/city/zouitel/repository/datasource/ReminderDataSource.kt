package city.zouitel.repository.datasource

import kotlinx.coroutines.flow.Flow
import city.zouitel.repository.model.Reminder as InReminder

interface ReminderDataSource {

    suspend fun observeById(uid: String): Flow<List<InReminder>>

    suspend fun insert(reminder: InReminder)

    suspend fun update(reminder: InReminder)

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}