package city.zouitel.repository.datasource

import city.zouitel.repository.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * Interface for accessing and managing media data.
 *
 * This interface defines methods for observing changes to media collections,
 * retrieving media by unique identifiers (UIDs), inserting new media, and
 * deleting media by their ID.
 */
interface MediaDataSource {
    /**
     * A [Flow] that emits a list of all available [Media] items.
     *
     * This flow will emit a new list whenever the underlying set of media items changes.
     * It provides a stream of all media data, allowing for reactive updates to UI or other
     * components that depend on the full collection of media.
     *
     * The emitted lists are **not guaranteed to be ordered** in any specific way,
     * unless sorting is explicitly applied downstream.
     *
     * **Note:** This flow can be potentially large, as it emits *all* media items.
     * Consider using more targeted flows if you only need a subset of media or changes to specific media.
     */
    val observeAll: Flow<List<Media>>

    /**
     * Observes a stream of media items associated with a specific user ID.
     *
     * This function returns a [Flow] that emits lists of [Media] objects whenever the media data
     * for the given user ID changes. It allows for reactive updates to the UI or other parts of
     * the application whenever new media is added, removed, or modified for that user.
     *
     * @param uid The unique identifier of the user whose media should be observed.
     * @return A [Flow] emitting lists of [Media] objects related to the specified `uid`.
     *         The flow will emit an empty list if no media is found for the user initially.
     *         The flow continues to emit new lists as the media data changes.
     *         The flow will never complete normally, unless the underlying data source is closed.
     */
    suspend fun observeByUid(uid: String): Flow<List<Media>>

    /**
     * Inserts a new [Media] object into the data store.
     *
     * This function is responsible for adding the provided [Media] object to the underlying
     * storage mechanism. It handles all necessary logic for persisting the media data,
     * such as generating unique identifiers (if needed) and updating any relevant indices or
     * metadata.
     *
     * @param media The [Media] object to be inserted. This object contains all the relevant
     *              information about the media, such as its type, location, and other metadata.
     * @throws IllegalArgumentException if the provided [media] object is invalid or if required
     *                                  fields are missing.
     * @throws IllegalStateException if there's an issue with the underlying storage that prevents
     *                                 the insertion from completing.
     * @throws Exception if any other error happens during the insertion process.
     * @return Unit, indicating successful completion. The inserted [Media] object might be modified,
     *         for example with a generated id.
     */
    suspend fun insert(media: Media)

    /**
     * Deletes an entity from the data source by its unique identifier.
     *
     * This function removes the entity associated with the given [id] from the underlying
     * storage. If no entity with the specified ID exists, no action is taken.
     *
     * @param id The unique identifier of the entity to be deleted.
     * @throws SomeExceptionType if there's an error during the deletion process (replace with actual exception type if any).
     * @throws AnotherExceptionType if another type of error can happen (replace with actual exception type if any).
     * @return void. The function doesn't return any value.
     */
    suspend fun deleteById(id: Long)

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
     *
     * @see saveDraft
     * @see getDrafts
     */
    suspend fun deleteDrafts()

    /**
     * Retrieves a list of draft IDs.
     *
     * This function fetches a list of identifiers representing drafts.
     * Each identifier is a unique string that can be used to retrieve
     * the full draft content from a data source.
     *
     * This function is a suspending function, meaning it can be paused
     * and resumed without blocking the main thread.  It is intended
     * to be used within a coroutine scope.
     *
     * @return A list of strings, where each string is a unique identifier
     *         for a draft. Returns an empty list if no drafts are found.
     *
     * @throws Exception If an error occurs while retrieving the drafts, such as network issues
     *         or database errors. Specific exception types may vary depending on the underlying
     *         implementation.
     */
    suspend fun getDrafts(): List<String>
}