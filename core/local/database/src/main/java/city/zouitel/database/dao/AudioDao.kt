package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Audio
import city.zouitel.database.model.Audio.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the [Audio] entity in the database.
 * Provides methods for observing, inserting, and deleting audio records.
 */
@Dao
interface AudioDao {

    /**
     * Observes all [Audio] entities from the database.
     *
     * This property returns a [Flow] that emits a list of [Audio] objects whenever the
     * underlying data in the `$TABLE_NAME` table changes.  It uses a database query to
     * select all rows from the table.
     *
     * Note: This flow will continuously emit new lists of [Audio] objects as the database
     * changes. Be mindful of resource consumption when collecting this flow, especially in
     * UI contexts.
     *
     * @return A [Flow] emitting a [List] of all [Audio] entities.
     */
    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Audio>>

    /**
     * Observes a single [Audio] entity from the database matching the given UUID.
     *
     * This function queries the `$TABLE_NAME` table for an entry where the `$UUID` column
     * matches the provided [uid]. It then emits the corresponding [Audio] object as a stream
     * via a [Flow]. If no matching entry is found, the [Flow] will emit nothing and complete.
     *
     * @param uid The UUID of the [Audio] entity to observe.
     * @return A [Flow] emitting the matching [Audio] entity, or nothing if not found.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<Audio>

    /**
     * Inserts or updates an [Audio] entity in the data store.
     *
     * If an [Audio] with the same primary key (e.g., audioId) already exists,
     * this function will update the existing record with the new data.
     * Otherwise, it will insert a new [Audio] record.
     *
     * This function is marked as a suspending function, meaning it should be
     * called within a coroutine or another suspending function.
     *
     * @param audio The [Audio] entity to be inserted or updated.
     */
    @Upsert
    suspend fun insert(audio: Audio)

    /**
     * Deletes a record from the [TABLE_NAME] table based on the provided UUID.
     *
     * @param uid The UUID of the record to delete.
     * @throws Exception If any error occurs during the database operation.
     * @return Unit. The function returns nothing explicitly, but it suspends until the database operation is complete.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $UUID = :uid")
    suspend fun deleteByUid(uid: String)

    /**
     * Deletes a row from the table with the specified ID.
     *
     * @param id The ID of the row to delete.
     * @throws SQLiteException if an error occurs during the database operation.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Long)
}