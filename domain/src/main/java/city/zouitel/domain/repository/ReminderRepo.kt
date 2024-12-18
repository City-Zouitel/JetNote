package city.zouitel.domain.repository

import city.zouitel.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing [Reminder]s.
 * Provides methods for data access and observation.
 */
interface ReminderRepo {
    /**
     * Observes reminders for a specific user ID.
     *
     * This function returns a [Flow] that emits a list of Reminder objects
     * associated with the given user ID whenever there are changes in the data.
     *
     * @param uid The unique identifier of the user.
     * @return A [Flow] emitting lists of Reminder objects for the user.
     */
    suspend fun observeById(uid: String): Flow<List<Reminder>>

    /**
     * Inserts a new reminder into the data source.
     *
     * @param reminder The reminder to be inserted.
     */
    suspend fun insert(reminder: Reminder)

    /**
     * Updates an entity with the given ID.
     *
     * @param id The ID of the entity to update.
     */
    suspend fun update(id: Int)

    /**
     * Deletes an entity with the given ID.
     *
     * @param id The ID of the entity to delete.
     */
    suspend fun delete(id: Int)

    /**
     * Deletes all entries from the data source.
     */
    suspend fun deleteAll()
}