package city.zouitel.domain.repository

import city.zouitel.domain.model.Audio
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing audio data.
 *
 * This interface defines methods for observing, inserting, and deleting audio records.
 */
interface AudioRepo {

    /**
     * A [Flow] that emits a list of all [Audio] objects in the data source.
     *
     * Each emission represents the current state of all audio items.
     * The list may contain `null` elements if there are gaps or errors in the data.
     *
     * This flow is typically used to observe changes in the entire collection of audio
     * data over time. Changes include additions, removals, and updates to individual
     * audio items.
     *
     * Note: Emitted lists may be empty if there are no audio files.
     * Note: It's the responsibility of the consumer to handle the `null` values in the list.
     */
    val observeAll: Flow<List<Audio?>>

    /**
     * Observes the [Audio] object associated with the given [uid].
     *
     * This function returns a [Flow] that emits [Audio] objects whenever the data
     * associated with the provided [uid] changes. If no [Audio] is found for the
     * given [uid], the flow will emit `null`.
     *
     * The flow will continue to emit updates until it is cancelled.
     *
     * @param uid The unique identifier of the [Audio] object to observe.
     * @return A [Flow] of nullable [Audio] objects. Emits `null` if no audio is found for the given [uid].
     */
    fun observeByUid(uid: String): Flow<Audio?>

    /**
     * Inserts a new [Audio] record into the data store.
     *
     * This is a suspend function, meaning it must be called within a coroutine or
     * another suspend function.  It will handle the necessary asynchronous operations
     * to safely insert the provided audio data.
     *
     * @param audio The [Audio] object to be inserted. Must not be null.
     * @throws Exception if there's an error during the insertion process, such as database issues.
     */
    suspend fun insert(audio: Audio)

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