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
     * Observes all reminders associated with a specific note uid.
     *
     * @param uid: The unique identifier of the user.
     * @return A Flow emitting a list of reminders for the given note uid.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Reminder>>

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
     * @param id The ID of the record to updateById.
     */
    @Query("UPDATE $TABLE_NAME SET $PASSED = 1 WHERE $ID = :id")
    suspend fun updateById(id: Int)

    /**
     * Deletes a row from the database table based on the provided ID.
     *
     * @param id: The ID of the row to be deleted.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Int)

    /**
     * Deletes a record from the [TABLE_NAME] table based on the provided UUID.
     *
     * @param uid The UUID of the record to delete.
     * @throws Exception If any error occurs during the database operation.
     * @return Unit. The function returns nothing explicitly, but it suspends until the database operation is complete.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $UUID = :uid")
    suspend fun deleteByUid(uid: String)
}