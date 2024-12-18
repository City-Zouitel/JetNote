package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.Reminder
import city.zouitel.database.model.Reminder.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.PASSED
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Reminder entity.
 * Provides methods for interacting with the reminder database table.
 */
@Dao
interface ReminderDao {

    /**
     * Observes all reminders associated with a specific user ID.
     *
     * @param uid: The unique identifier of the user.
     * @return A Flow emitting a list of reminders for the given user ID.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeAllById(uid: String): Flow<List<Reminder>>

    /**
     * Inserts a new reminder into the database.
     *
     * @param reminder: The reminder to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reminder: Reminder)

    /**
     * Updates the record with the given ID, setting the 'passed' column to 1 (true).
     *
     * @param id The ID of the record to update.
     */
    @Query("UPDATE $TABLE_NAME SET $PASSED = 1 WHERE $ID = :id")
    suspend fun update(id: Int)

    /**
     * Deletes all rows from the reminder table.
     */
    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    /**
     * Deletes a row from the database table based on the provided ID.
     *
     * @param id: The ID of the row to be deleted.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun delete(id: Int)
}