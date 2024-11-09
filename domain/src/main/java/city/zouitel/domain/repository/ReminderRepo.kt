package city.zouitel.domain.repository

import city.zouitel.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepo {

    val observeAllReminders: Flow<List<Reminder>>

    suspend fun observeRemindersById(id: Long): Flow<List<Reminder>>

    suspend fun insertReminder(reminder: Reminder)

    suspend fun updateReminder(reminder: Reminder)

    suspend fun deleteReminderById(id: Long)

    suspend fun deleteAllReminders()
}