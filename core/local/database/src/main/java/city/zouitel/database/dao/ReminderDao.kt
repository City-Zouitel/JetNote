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
     * @param id The ID of the record to update.
     */
    @Query("UPDATE $TABLE_NAME SET $PASSED = 1 WHERE $ID = :id")
    suspend fun update(id: Int)

    /**
     * Deletes a row from the database table based on the provided ID.
     *
     * @param id: The ID of the row to be deleted.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun delete(id: Int)

    /**
     * Deletes draft notes from the database.
     *
     * This function removes notes that are considered drafts, meaning they
     * are present in the main table (`TABLE_NAME`) but are not linked to any
     * existing note data in the `note_data_table`. This effectively cleans up
     * incomplete or abandoned notes that were not properly saved.
     *
     * The deletion is performed using a SQL `DELETE` query with a subquery
     * to identify drafts. The subquery selects the UUIDs from the `note_data_table`,
     * and the outer query then deletes any records from `TABLE_NAME` whose UUID
     * is not present in that result set.
     *
     * This function should be called within a coroutine scope because it's a
     * suspend function.
     *
     * @throws SQLiteException If an error occurs while executing the database query.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $UUID NOT IN (SELECT $UUID FROM note_data_table)")
    suspend fun deleteDrafts()
}