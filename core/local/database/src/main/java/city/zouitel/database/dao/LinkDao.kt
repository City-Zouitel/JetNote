package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Link
import city.zouitel.database.model.Link.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the [Link] entity in the database.
 * This interface defines methods for observing, inserting, and deleting Link data.
 */
@Dao
interface LinkDao {

    /**
     *  Observes all [Link] entities from the database table specified by [TABLE_NAME].
     *
     *  This flow emits a new list of [Link] objects whenever the underlying data in the table changes.
     *
     *  @return A [Flow] that emits a list of [Link] objects.
     *  @see TABLE_NAME
     */
    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Link>>

    /**
     * Observes all [Link] entities from the database that match the given [uid].
     *
     * This function provides a reactive stream of [Link] lists.  Whenever the database
     * table corresponding to [Link] is modified (inserted, updated, or deleted) and
     * any row's [UUID] column matches the provided [uid], a new list representing
     * the current state of matching entities will be emitted to the [Flow].
     *
     * @param uid The unique identifier ([UUID]) of the [Link] entities to observe.
     * @return A [Flow] emitting lists of [Link] objects matching the given [uid].
     *         The list will be empty if no matching entities are found.
     *         The flow will emit new lists whenever the data in the database changes.
     */
    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Link>>

    /**
     * Inserts a [Link] into the database, or updates it if a matching row already exists.
     *
     * This function utilizes Room's `@Upsert` annotation, which provides a convenient way to
     * either insert a new entity or updateById an existing one based on the entity's primary key.
     * If a [Link] with the same primary key (as defined in the [Link] entity class) already
     * exists in the database, it will be updated with the new values provided in the [link]
     * parameter. Otherwise, a new row will be inserted.
     *
     * This is a suspend function and must be called from within a coroutine or another
     * suspend function.
     *
     * @param link The [Link] object to be inserted or updated in the database.
     * @throws SQLiteException if there's an issue interacting with the underlying database.
     * @see Upsert
     * @see Link
     */
    @Upsert(entity = Link::class)
    suspend fun insert(link: Link)

    /**
     * Deletes a row from the table with the specified ID.
     *
     * @param id The ID of the row to deleteById.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Int)

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