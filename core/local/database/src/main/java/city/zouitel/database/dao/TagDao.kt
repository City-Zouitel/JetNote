package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Tag
import city.zouitel.database.model.Tag.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the [Tag] entity in the database.
 *
 * This interface defines methods for observing, inserting, and deleting tags.
 */
@Dao
interface TagDao {
    /**
     * Observes all tags in the database.
     *
     * This property provides a [Flow] that emits a list of all [Tag] entities
     * stored in the database. The flow will emit a new list whenever the
     * underlying data in the table changes.
     *
     * @return A [Flow] of [List] of [Tag] entities.
     */
    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Tag>>

    /**
     * Inserts a new [Tag] into the data store or updates an existing one if it already exists.
     *
     * This function uses an upsert operation, meaning it will either insert a new row with the
     * provided [Tag] data if no matching record is found, or update the existing row that matches the
     * [Tag]'s primary key (or other identifying attributes) with the provided data.
     *
     * @param tag The [Tag] object to be inserted or updated.
     */
    @Upsert
    suspend fun insert(tag: Tag)

    /**
     * Deletes a row from the table with the specified ID.
     *
     * @param id The ID of the row to delete.
     * @throws SQLiteException if an error occurs during the database operation.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Long)
}