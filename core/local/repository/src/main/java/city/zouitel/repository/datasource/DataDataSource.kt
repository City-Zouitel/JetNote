package city.zouitel.repository.datasource

import city.zouitel.repository.model.Data


/**
 * Interface defining the contract for interacting with a data source for [Data] objects.
 * This interface provides methods for basic CRUD (Create, Read, Update, Delete) operations,
 * as well as specialized operations for archiving, rolling back, and erasing data.
 */
interface DataDataSource {
    /**
     * Inserts a new [Data] object into the data store.
     *
     * This is a suspend function, meaning it must be called within a coroutine or
     * another suspend function.  It will potentially perform a long-running operation
     * such as database insertion or network request, and will suspend until the operation
     * is complete.
     *
     * @param data The [Data] object to be inserted.  Must not be null.
     * @throws Exception if the insertion fails for any reason (e.g., database error, network error, etc.).
     * @throws IllegalArgumentException if [data] is invalid. (Optional, if you have validation)
     * @return Nothing, but completes when the data has been successfully inserted.
     */
    suspend fun insert(data: Data)

    /**
     * Archives the specified user.
     *
     * Archiving a user typically means marking them as inactive or deleting their data in a way that it's
     * no longer readily accessible, but potentially still recoverable for auditing or legal reasons.
     *
     * This is a suspend function, meaning it should be called from a coroutine or another suspend function.
     *
     * @param uid The unique identifier of the user to archive.
     * @throws Exception If any error occurs during the archiving process. Specific error types might include:
     *   - `UserNotFoundException`: If a user with the given `uid` does not exist.
     *   - `DatabaseException`: If an error occurs while interacting with the database.
     *   - `PermissionDeniedException`: If the caller lacks the necessary permission to archive the user.
     *   - ... (Add more specific exception types as needed)
     * @throws IllegalArgumentException If the `uid` is null or empty.
     */
    suspend fun archive(uid: String)

    /**
     * Rolls back any pending operations associated with the given user ID.
     *
     * This function is typically used to undo or cancel actions that have been
     * initiated but not yet finalized for a specific user. This might include
     * things like partially completed transactions, temporary data allocations,
     * or queued operations.
     *
     * The implementation of this function should ensure that all side effects
     * of the pending operations are reversed, leaving the system in a consistent
     * state as if the operations had never been started.
     *
     * **Note:** This is a suspend function, meaning it can only be called from within a
     * coroutine or another suspend function. This allows for potentially long-running
     * operations to be performed without blocking the calling thread.
     *
     * @param uid The unique identifier of the user whose operations should be rolled back.
     * @throws SomeExceptionType If an error occurs during the rollback process. (Replace with actual exception types)
     * @throws AnotherExceptionType If another specific error occurs. (Replace with actual exception types)
     */
    suspend fun rollback(uid: String)

    /**
     * Deletes a record associated with the given unique identifier (UID).
     *
     * This is a suspend function, meaning it must be called within a coroutine or
     * another suspend function.  It will potentially perform long-running operations
     * (like network or database interaction) and will suspend execution until complete.
     *
     * @param uid The unique identifier of the record to delete. Must not be empty or blank.
     * @throws IllegalArgumentException if the `uid` is empty or blank.
     * @throws Exception if any error occurs during the deletion process (e.g., network error, database error, record not found).
     */
    suspend fun delete(uid: String)

    /**
     * Erases all data associated with the current context.
     *
     * This function permanently deletes all stored information, effectively resetting
     * the system or component to its initial state.  After calling this method,
     * any previously stored data will be irretrievable.
     *
     * This operation is **asynchronous** and **suspending**, meaning it may take some
     * time to complete and will not block the calling thread.  You must call this
     * function within a coroutine or another suspending function.
     *
     * **Important Considerations:**
     * - This is a destructive operation. Exercise caution when invoking it.
     * - Any ongoing operations relying on the data being erased may be disrupted or fail.
     * - Consider providing a confirmation step to the user before calling this function, if applicable.
     * - If there are specific cleanup tasks related to the data being erased (e.g., releasing resources),
     *   ensure they are handled within the implementation of this function.
     *
     * @throws Exception If any error occurs during the erasure process. Specific exceptions may vary
     *                   depending on the underlying implementation.
     */
    suspend fun erase()
}