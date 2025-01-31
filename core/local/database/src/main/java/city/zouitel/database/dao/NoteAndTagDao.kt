package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.NoteAndTag
import city.zouitel.database.model.NoteAndTag.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the NoteAndTag entity in the database.
 * This DAO provides methods to query, insert, and delete NoteAndTag entries.
 *
 * @see NoteAndTag
 */
@Dao
interface NoteAndTagDao {

    /**
     * Observes all [NoteAndTag] entities from the database.
     *
     * This property provides a [Flow] that emits a new list of [NoteAndTag] whenever the underlying
     * data in the database changes.  It retrieves all rows from the database table specified by
     * [TABLE_NAME]. Each emitted list will contain all the [NoteAndTag] objects currently present.
     *
     * @return A [Flow] of [List<NoteAndTag>] representing all notes and their associated tags in the database.
     * @see TABLE_NAME
     */
    @get: Query("SELECT * FROM $TABLE_NAME")
    val observeAll:Flow<List<NoteAndTag>>

    /**
     * Inserts a [NoteAndTag] object into the database.
     *
     * This function attempts to insert a [NoteAndTag] instance, which represents the association
     * between a note and a tag. If a conflict arises during the insertion (i.e., a note and tag pair with
     * the same identifying characteristics already exists), the insertion will be ignored. This is
     * specified by the [OnConflictStrategy.IGNORE] strategy.
     *
     * @param noteAndLabel The [NoteAndTag] object to be inserted.
     * @see OnConflictStrategy.IGNORE
     * @see NoteAndTag
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteAndLabel: NoteAndTag)

    /**
     * Deletes a row from the table with the specified unique ID.
     *
     * @param uid The unique ID (uid) of the row to delete.
     * @throws Exception If any database operation fails.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE uid = :uid")
    suspend fun deleteByUid(uid: String)

    /**
     * Deletes a row from the [TABLE_NAME] table based on the provided ID.
     *
     * @param id The ID of the row to delete.
     * @throws Exception if any error occurs during the deletion process.
     */
    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteById(id: Long)
}