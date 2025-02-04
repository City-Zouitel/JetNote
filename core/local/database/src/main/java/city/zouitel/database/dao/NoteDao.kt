package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import city.zouitel.database.model.Data.Companion.TABLE_NAME
import city.zouitel.database.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the [Note] entity.
 * Provides methods for querying and observing [Note] data from the database.
 */
@Dao
interface NoteDao {
    /**
     * Retrieves and observes a flow of unarchived notes from the database, ordered by date in ascending order.
     *
     * This function performs the following actions:
     * 1. **Filters unarchived notes:** It selects notes where the `ARCHIVED` column is 0 (false).
     * 2. **Orders by date:** It sorts the selected notes in ascending order based on the `DATE` column.
     * 3. **Returns a Flow:** It returns a `Flow` that emits a list of `Note` objects whenever there are changes in the underlying data.
     * 4. **Transaction:** Executes the query within a database transaction, ensuring data consistency.
     *
     * @return A [Flow] emitting a list of [Note] objects that are not archived, sorted by date in ascending order.
     *
     * @see Transaction
     * @see Query
     * @see Flow
     * @see Note
     * @see TABLE_NAME
     */
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE ARCHIVED = 0 ORDER BY DATE ASC")
    fun observeByDefault(): Flow<List<Note>>

    /**
     * Observes a list of unarchived notes, sorted by date in descending order (newest first).
     *
     * This function queries the database for all notes where the `ARCHIVED` field is `0` (false),
     * indicating that the notes are not archived. The results are then sorted in descending order
     * based on the `DATE` field, so the most recently created or modified notes appear first.
     *
     * The results are returned as a [Flow] of [List] of [Note] objects. This means that any changes
     * to the underlying data will be automatically emitted as new lists through the flow.
     *
     * @return A [Flow] that emits a [List] of [Note] objects ordered by `DATE` in descending order,
     *         containing only unarchived notes.
     */
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE ARCHIVED = 0 ORDER BY DATE DESC")
    fun observeByNewest(): Flow<List<Note>>

    /**
     * Data Access Object (DAO) interface for interacting with [Note] entities in the Room database.
     */
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE ARCHIVED = 0 ORDER BY DATE ASC")
    fun observeByOldest(): Flow<List<Note>>

    /**
     * Retrieves all unarchived notes from the database, sorted alphabetically by their title.
     *
     * This function provides a reactive stream of [Note] objects, emitting a new list whenever the underlying
     * data changes.  It only returns notes where the `ARCHIVED` flag is set to `0` (false).
     * The returned list is ordered in ascending order based on the `TITLE` property of each note.
     *
     * This method is annotated with `@Transaction` to ensure that all operations performed within the query
     * are executed as a single atomic transaction.
     *
     * @return A [Flow] emitting a list of [Note] objects that are not archived and are sorted by title in ascending order.
     *     Returns an empty list if no unarchived notes exist.
     */
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE ARCHIVED = 0 ORDER BY TITLE ASC")
    fun observeByName(): Flow<List<Note>>

    /**
     * Observes a list of unarchived notes, sorted by priority in descending order (highest first).
     *
     * This function retrieves all notes from the database where the `ARCHIVED` flag is 0 (false),
     * indicating they are not archived.  It then sorts them based on their `PRIORITY` value.
     * The sorting logic is as follows:
     *
     * - Priority 4 (highest) is sorted first.
     * - Priority 3 is sorted second.
     * - Priority 2 is sorted third.
     * - Priority 1 is sorted fourth.
     * - Priority 0 (lowest) is sorted last.
     *
     * The result is returned as a `Flow` of `List<Note>`, allowing for reactive updates
     * whenever the underlying data changes.
     *
     * @return A [Flow] emitting lists of [Note] objects, sorted by priority and filtered for unarchived notes.
     * @see Note
     */
    @Transaction
    @Query("""
        SELECT * FROM $TABLE_NAME WHERE ARCHIVED = 0 ORDER BY CASE
            WHEN PRIORITY LIKE 4 THEN 1
            WHEN PRIORITY LIKE 3 THEN 2
            WHEN PRIORITY LIKE 2 THEN 3
            WHEN PRIORITY LIKE 1 THEN 4
            WHEN PRIORITY LIKE 0 THEN 5
            END
            """)
    fun observeByPriority(): Flow<List<Note>>

    /**
     * Retrieves and observes a flow of archived notes from the database.
     *
     * This function executes a database query to select all notes where the `ARCHIVED` column is set to 1,
     * indicating that the notes have been archived.  The result is provided as a [Flow] of a [List] of [Note] objects.
     *
     * The function is annotated with [Transaction] which ensures that all database operations performed within the
     * query are executed as a single atomic transaction. This is important for data consistency, especially if
     * this query is part of a larger set of database interactions.
     *
     * The flow emits a new list of [Note] whenever the underlying data in the database changes.
     *
     * @return A [Flow] emitting a [List] of [Note] objects that are archived.
     */
    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE ARCHIVED = 1")
    fun observeArchives(): Flow<List<Note>>
}