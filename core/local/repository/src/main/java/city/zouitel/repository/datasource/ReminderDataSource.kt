package city.zouitel.repository.datasource

import kotlinx.coroutines.flow.Flow
import city.zouitel.repository.model.Reminder as InReminder

interface ReminderDataSource {

    val observeAllReminders: Flow<List<InReminder>>

    suspend fun observeRemindersById(id: Long): Flow<List<InReminder>>

    suspend fun insertReminder(reminder: InReminder)

    suspend fun updateReminder(reminder: InReminder)

    suspend fun deleteReminderById(id: Long)

    suspend fun deleteAllReminders()
}