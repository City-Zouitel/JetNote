package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.Media
import city.zouitel.database.model.Media.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.URI
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
     * @param id The ID of the row to delete.
     * @return The number of rows deleted. Usually 1 if successful, 0 if no row with the given ID exists.
     *
     * @throws SQLiteException if an error occurs during the database operation.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun delete(id: Long)

    /**
     * Retrieves a list of URIs representing drafts.
     *
     * Drafts are defined as notes that exist in the main notes table
     * (represented by $TABLE_NAME and $URI) but do not have corresponding
     * entries in the 'note_data_table'. This indicates that these notes have
     * been created but have not yet had their detailed data saved.
     *
     * This function uses a SQL subquery to identify drafts:
     *   - `SELECT $URI FROM $TABLE_NAME`: Selects the URI column from the main notes table.
     *   - `WHERE $UUID NOT IN (SELECT $UUID FROM note_data_table)`: Filters the results to only include notes whose UUIDs are not present in the 'note_data_table'.
     *
     * @return A list of Strings, where each String is the URI of a draft note.
     *
     * @see TABLE_NAME
     * @see URI
     * @see UUID
     * @see note_data_table
     */
    @Query("SELECT $URI FROM $TABLE_NAME WHERE $UUID NOT IN (SELECT $UUID FROM note_data_table)")
    suspend fun getDrafts(): List<String>

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