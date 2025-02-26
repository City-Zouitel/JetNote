package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.Media
import city.zouitel.database.model.Media.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the [Media] entity in the Room database.
 * Provides methods for observing, inserting, updating, and deleting media entries.
 */
@Dao
interface MediaDao {

    /**
     * Observes all [Media] entities from the database.
     *
     * This property returns a [Flow] that emits a list of [Media] objects whenever
     * the underlying data in the `$TABLE_NAME` table changes.  It effectively provides
     * a stream of all media items stored in the database.
     *
     * The query used is "SELECT * FROM `$TABLE_NAME`", which retrieves all columns
     * and rows from the media table.
     *
     * @return A [Flow] of [List<Media>] representing all media entities in the database.
     *         The list is updated whenever the underlying data changes.
     */
    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Media>>

    /**
     * Observes a list of [Media] objects from the database that match a specific UUID.
     *
     * This function provides a reactive stream of [Media] lists. Whenever the data
     * associated with the provided `uid` in the database changes, a new list of matching
     * [Media] objects will be emitted by the returned [Flow].
     *
     * @param uid The UUID of the media objects to observe.
     * @return A [Flow] emitting a list of [Media] objects that match the given `uid`.
     *         If no media objects are found with the specified `uid`, an empty list will be emitted.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Media>>

    /**
     * Inserts a [Media] object into the database.
     *
     * If a conflict occurs (i.e., a row with the same primary key already exists),
     * the existing row will be ignored, and no changes will be made.
     *
     * This is a suspend function, so it must be called within a coroutine.
     *
     * @param media The [Media] object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(media: Media)

    /**
     * Deletes a row from the table with the specified ID.
     *
     * @param id The ID of the row to deleteById.
     * @return The number of rows deleted. Usually 1 if successful, 0 if no row with the given ID exists.
     *
     * @throws SQLiteException if an error occurs during the database operation.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Long)

    /**
     * Deletes a record from the [TABLE_NAME] table based on the provided UUID.
     *
     * @param uid The UUID of the record to deleteById.
     * @throws Exception If any error occurs during the database operation.
     * @return Unit. The function returns nothing explicitly, but it suspends until the database operation is complete.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $UUID = :uid")
    suspend fun deleteByUid(uid: String)
}