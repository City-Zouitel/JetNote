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
    suspend fun update(id: Int)

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
    suspend fun delete(id: Int)

    /**
     * Deletes all draft messages from the local storage.
     *
     * This function asynchronously removes all messages that are marked as drafts.
     * It's typically used when a user wants to clear all unfinished message compositions.
     *
     * Note: This operation is performed locally and does not affect any server-side
     * data.  It only removes the locally stored drafts.
     *
     * @throws Exception if there's an error during the deletion process, such as a database access issue.
     */
    suspend fun deleteDrafts()
}