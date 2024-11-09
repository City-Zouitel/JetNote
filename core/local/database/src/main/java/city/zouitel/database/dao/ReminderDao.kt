package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @get:Query("SELECT * FROM REMINDERS_TABLE")
    val observeAllReminders: Flow<List<ReminderEntity>>

    @Query("SELECT * FROM REMINDERS_TABLE WHERE id = :id")
    fun observeAllReminderById(id: Long): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReminder(reminder: ReminderEntity)

    @Query("DELETE FROM ${ReminderEntity.TABLE_NAME}")
    suspend fun deleteAllReminders()

    @Query("DELETE FROM ${ReminderEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteReminderById(id: Long)
}