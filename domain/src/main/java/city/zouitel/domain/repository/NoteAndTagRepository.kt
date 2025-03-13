package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndTag
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing the relationship between Notes and Tags.
 * This repository provides methods for interacting with the database to
 * insert, delete, and observe [NoteAndTag] entities.
 */
interface NoteAndTagRepository {
    /**
     * A [Flow] that emits a list of [NoteAndTag] objects.
     * This flow observes all changes in the underlying data source and emits an updated list whenever
     * any note or tag, or their relationship, is modified.
     *
     * Each [NoteAndTag] object represents a note and the associated tags linked to it.
     * This can be used to efficiently keep the UI updated with the latest data from the database.
     *
     * The list will be empty if there are no notes or if no notes have associated tags.
     *
     * The flow will continue to emit new lists of [NoteAndTag] as changes occur.
     */
    val observeAll: Flow<List<NoteAndTag>>

    /**
     * Inserts a [NoteAndTag] relationship into the database.
     *
     * This function is a suspend function, meaning it must be called within a coroutine.
     * It atomically creates or updates a link between a note and a tag. If a note or tag does not exist,
     * it's expected that they will be created separately before invoking this method. This function
     * primarily focuses on establishing the connection between pre-existing notes and tags.
     *
     * @param noteAndTag The [NoteAndTag] object representing the relationship between a note and a tag to be inserted.
     *                  It should contain valid `noteId` and `tagId` values.
     *
     * @throws Exception if there's an issue inserting the NoteAndTag relationship, e.g., database error.
     */
    suspend fun insert(noteAndTag: NoteAndTag)

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