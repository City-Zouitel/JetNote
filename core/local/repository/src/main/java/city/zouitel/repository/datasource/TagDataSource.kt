package city.zouitel.repository.datasource

import city.zouitel.repository.model.Tag
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for a data source that manages [Tag] entities.
 * This data source provides methods for observing, inserting, and deleting tags.
 */
interface TagDataSource {
    /**
     * A [Flow] that emits a list of all [Tag]s whenever the underlying data changes.
     *
     * This flow provides a stream of the complete set of tags. Any changes to the tags,
     * such as additions, deletions, or modifications, will trigger a new emission
     * containing the updated list of all tags.
     *
     * Note: The order of the tags in the emitted list is not guaranteed to be consistent
     * unless the underlying data source provides a specific ordering.
     */
    val observeAll: Flow<List<Tag>>

    /**
     * Inserts a new [Tag] into the data store.
     *
     * This function is a suspending function, meaning it must be called within a coroutine or
     * another suspending function.  It will handle the necessary operations to persist the
     * given [Tag] to the underlying storage mechanism (e.g., a database).
     *
     * @param tag The [Tag] object to be inserted. Must not be null.
     *
     * @throws Exception if there is an error during the insertion process, such as a database
     *                   connection failure or a constraint violation. The specific type of exception
     *                   will depend on the underlying implementation.
     */
    suspend fun insert(tag: Tag)

    /**
     * Deletes an entity from the data source by its unique identifier.
     *
     * This is a suspending function, meaning it should be called within a coroutine or
     * another suspending function.  It handles the asynchronous deletion operation.
     *
     * @param id The unique identifier of the entity to delete. Must not be negative.
     * @throws IllegalArgumentException If the provided [id] is negative.
     * @throws NoSuchElementException If no entity is found with the given [id].
     * @throws Exception If any other error occurs during the deletion process (e.g., database error).
     */
    suspend fun deleteById(id: Long)
}