package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Data
import city.zouitel.database.model.Data.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.UUID

/**
 * Data Access Object (DAO) for interacting with the [Data] entity in the database.
 *
 * This interface provides methods for inserting, updating, archiving, unarchiving (rollback),
 * deleting, and erasing data records.
 */
@Dao
interface DataDao {
    /**
     * Inserts a [Data] object into the database. If a [Data] object with the same primary key
     * already exists, it will be replaced (updated) with the new data.
     *
     * This is a suspend function, meaning it must be called from a coroutine or another suspend function.
     *
     * @param data The [Data] object to insert or update.
     */
    @Upsert
    suspend fun insert(data: Data)

    /**
     * Archives a record in the database by setting its `ARCHIVED` flag to 1.
     *
     * This effectively marks the record with the given [uid] as archived,
     * without physically deleting it from the database.
     *
     * @param uid The unique identifier (UUID) of the record to archive.
     * @throws Exception if there is an issue with database interaction.
     */
    @Query("UPDATE $TABLE_NAME SET ARCHIVED = 1 WHERE $UUID = :uid")
    suspend fun archive(uid: String)

    /**
     * Unarchives (rolls back) an item in the database.
     *
     * This function updates the `ARCHIVED` column of the item identified by the given `uid` to 0, effectively
     * marking it as not archived.
     *
     * @param uid The unique identifier (UUID) of the item to unarchive.
     */
    @Query("UPDATE $TABLE_NAME SET ARCHIVED = 0 WHERE $UUID = :uid")
    suspend fun rollback(uid: String)

    /**
     * Deletes a record from the table with the specified UUID.
     *
     * @param uid The UUID of the record to delete.
     * @throws Exception if an error occurs during the deletion process.  (Optional - add if you have specific exceptions you throw)
     * @return The number of rows deleted (should be 0 or 1 in this case, as UUID is generally unique). (Optional - add if Room explicitly returns this info, or if you add error handling and want to return it)
     */
    @Query("DELETE FROM $TABLE_NAME WHERE $UUID = :uid")
    suspend fun delete(uid: String)

    /**
     * Deletes all rows from the table where the `ARCHIVED` column is set to `1` (true).
     * This effectively removes all archived entries from the persistent storage.
     *
     * This function is a suspend function, meaning it must be called within a coroutine or another suspend function.
     * It operates directly on the database and does not return any specific information other than the successful completion of the deletion.
     *
     * @see [TABLE_NAME] for the name of the table this operation affects.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE ARCHIVED = 1")
    suspend fun erase()
}