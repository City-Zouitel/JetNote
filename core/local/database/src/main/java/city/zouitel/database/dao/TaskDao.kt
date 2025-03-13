package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Task
import city.zouitel.database.model.Task.Companion.TABLE_NAME
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
     * Inserts a [Task] into the database, or updates it if a [Task] with the same primary key already exists.
     *
     * This is a suspend function, meaning it must be called within a coroutine.
     *
     * @param item The [Task] to be inserted or updated.
     * @throws SQLiteConstraintException if a constraint violation occurs during the upsert operation.
     */
    @Upsert(entity = Task::class)
    suspend fun insert(item: Task)

    /**
     * Deletes a row from the table with the specified ID.
     *
     * @param id The ID of the row to delete.
     * @throws SQLiteException if an error occurs during the database operation.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun delete(id: Long)

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