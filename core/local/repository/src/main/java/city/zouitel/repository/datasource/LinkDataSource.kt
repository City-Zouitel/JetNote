package city.zouitel.repository.datasource

import city.zouitel.repository.model.Link
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the data source for managing links.
 * This interface provides methods for observing, inserting, and deleting link data.
 */
interface LinkDataSource {

    /**
     * A [Flow] that emits a list of all [Link]s in the data source.
     *
     * This flow will emit a new list whenever the underlying data changes.
     * The emitted list represents a snapshot of all available links at the time of emission.
     *
     * Note: This flow will continue emitting updates as long as it is collected.
     * Consider using operators like `take()` or `first()` if you only need a single value or a limited number of emissions.
     *
     * @see Link
     */
    val observeAll: Flow<List<Link>>

    /**
     * Observes a list of [Link]s associated with a specific user identified by their UID (User ID).
     *
     * This function returns a [Flow] that emits a new list of [Link]s whenever the data associated
     * with the given `uid` changes in the underlying data source.  The flow will continue emitting
     * updates until the collector is cancelled or the data source is no longer accessible.
     *
     * The emitted lists are ordered according to the natural ordering defined by the underlying data source,
     * if any.  If no links are found associated with the UID, an empty list will be emitted.
     *
     * @param uid The unique identifier (UID) of the user whose links are being observed.
     * @return A [Flow] emitting a list of [Link]s associated with the given `uid`.
     */
    suspend fun observeByUid(uid: String): Flow<List<Link>>

    /**
     * Inserts a new [Link] into the data store.
     *
     * This function is a suspending function, meaning it can be safely called from a coroutine
     * and will not block the calling thread while it performs the insertion.
     *
     * @param link The [Link] object to be inserted.
     * @throws Exception if any error occurs during the insertion process.
     */
    suspend fun insert(link: Link)

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