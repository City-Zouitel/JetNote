package city.zouitel.domain.repository

import city.zouitel.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing [Reminder]s.
 * Provides methods for data access and observation.
 */
interface ReminderRepo {
    /**
     * Observes reminders for a specific note UID.
     *
     * This function returns a [Flow] that emits a list of Reminder objects
     * associated with the given note UID whenever there are changes in the data.
     *
     * @param uid The unique identifier of the user.
     * @return A [Flow] emitting lists of Reminder objects for the user.
     */
    suspend fun observeByUid(uid: String): Flow<List<Reminder>>

    /**
     * Inserts a new reminder into the data source.
     *
     * @param reminder The reminder to be inserted.
     */
    suspend fun insert(reminder: Reminder)

    /**
     * Updates an entity with the given [id].
     *
     * @param id The identifier of the entity to update.
     */
    suspend fun updateById(id: Int)

    /**
     * Deletes an entity from the data source by its unique identifier.
     *
     * This is a suspending function, meaning it should be called within a coroutine or
     * another suspending function.  It handles the asynchronous deletion operation.
     *
     * @param id The unique identifier of the entity to delete. Must be a positive integer.
     * @throws IllegalArgumentException if the provided `id` is not valid (e.g., negative or zero).
     * @throws NoSuchElementException if no entity with the given `id` is found. (Optional, depends on implementation)
     * @throws Exception if an error occurs during the deletion process (e.g., database error, network error). (Optional, depends on implementation)
     */
    suspend fun deleteById(id: Int)

    /**
     * Deletes a record or resource associated with the given unique identifier (UID).
     *
     * This is a suspending function, meaning it must be called within a coroutine or another
     * suspending function. It performs the deletion asynchronously.
     *
     * @param uid The unique identifier of the record/resource to delete.
     * @throws Exception if any error occurs during the deletion process.  Consider more specific exceptions if possible.
     * For example:
     * @throws NotFoundException if a record with the given UID is not found.
     * @throws DatabaseException if there is an issue communicating with the database.
     * @throws PermissionDeniedException if the user doesn't have permission to delete.
     * @throws IllegalArgumentException if the uid is invalid or empty.
     *
     * @see [suspend]
     * @see [kotlinx.coroutines]
     */
    suspend fun deleteByUid(uid: String)
}