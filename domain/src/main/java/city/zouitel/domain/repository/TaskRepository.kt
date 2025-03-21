package city.zouitel.domain.repository

import city.zouitel.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for a data source that manages [Task] entities.
 * This data source provides methods for observing, inserting, updating, and deleting tasks.
 */
interface TaskRepository {

    /**
     * A [Flow] that emits a list of all [Task]s whenever the underlying data changes.
     *
     * This flow provides a stream of the complete list of tasks, allowing for reactive updates
     * in the UI or other parts of the application.  Any changes to the task data (addition,
     * removal, modification) will trigger a new emission from this flow containing the updated
     * list.
     *
     * It's crucial that any collection or data access within the source of this [Flow]
     * is handled efficiently and is thread-safe, as multiple collectors might be subscribing
     * concurrently.
     *
     * @see Task
     * @see Flow
     */
    val observeAll: Flow<List<Task>>

    /**
     * Observes a flow of tasks associated with a specific user ID.
     *
     * This function returns a [Flow] that emits a list of [Task] objects whenever the task list
     * for the given `uid` changes.  Changes can include additions, deletions, or modifications
     * to tasks associated with this user.
     *
     * The flow will continue to emit updates until the coroutine collecting the flow is cancelled.
     *
     * @param uid The unique identifier of the user whose tasks should be observed.
     * @return A [Flow] that emits a [List] of [Task] objects. An empty list is emitted if no tasks
     *         are found for the given user ID.
     * @throws IllegalArgumentException if the provided `uid` is blank or empty.
     */
    fun observeByUid(uid: String): Flow<List<Task>>

    /**
     * Inserts a new [Task] into the data source.
     *
     * This function is a suspend function, meaning it can be safely called from a coroutine and
     * will not block the main thread. It handles the asynchronous operation of inserting the task.
     *
     * @param task The [Task] to be inserted. Must not be null.
     * @throws Exception if any error occurs during the insertion process, such as a database error or
     *                    constraint violation.
     */
    suspend fun insert(task: Task)

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
    suspend fun delete(id: Long)

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