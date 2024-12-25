package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.Task
import city.zouitel.database.model.Task.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DONE
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the [Task] entity in the database.
 *
 * This interface provides methods for observing, inserting, updating, and deleting [Task] records.
 */
@Dao
interface TaskDao {

    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Task>>

    /**
     * Observes a list of [Task] entities from the database that match the given UUID.
     *
     * This function provides a reactive stream of [Task] lists. Whenever the data
     * associated with the given `uid` in the database changes, a new list will
     * be emitted through the returned [Flow].
     *
     * @param uid The UUID of the task to observe.
     * @return A [Flow] emitting a list of [Task] entities that match the given `uid`.
     *         If no tasks match the `uid`, an empty list is emitted.
     *         The flow will continue to emit new lists whenever the matching tasks change.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Task>>

    /**
     * Inserts a [Task] into the database.
     *
     * If a [Task] with the same primary key already exists, it will be replaced.
     *
     * @param item The [Task] to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Task)

    /**
     * Updates the 'done' status of a task in the database by toggling its current state.
     *
     * This function finds the task with the specified [id] and inverts the value of its 'done'
     * column (e.g., if 'done' was true, it will become false, and vice-versa).
     *
     * @param id The ID of the task to update.
     */
    @Query("UPDATE $TABLE_NAME SET $DONE = NOT $DONE WHERE $ID = :id")
    suspend fun updateById(id: Long)

    /**
     * Deletes a row from the table with the specified ID.
     *
     * @param id The ID of the row to delete.
     * @throws SQLiteException if an error occurs during the database operation.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Long)
}