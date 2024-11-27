package city.zouitel.domain.repository

import city.zouitel.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderDatasource {

    suspend fun observeById(uid: String): Flow<List<Reminder>>

    suspend fun insert(reminder: Reminder)

    suspend fun update(reminder: Reminder)

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}