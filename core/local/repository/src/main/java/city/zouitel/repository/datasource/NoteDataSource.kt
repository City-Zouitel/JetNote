package city.zouitel.repository.datasource

import city.zouitel.repository.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining a data source for observing changes in a collection of [Note] objects.
 * This interface provides various flows representing different ways to observe the note list
 * based on different sorting and filtering criteria.
 */
interface NoteDataSource {
    /**
     * A [Flow] of a list of [Note] objects. This flow emits a list of all notes
     * in the data source by default. It's typically used to observe changes in the
     * entire collection of notes.
     *
     * The emitted lists are always complete representations of the current state of notes,
     * meaning they reflect all notes in the underlying data store at the time of emission.
     *
     *  Note: Changes in individual notes will result in a new list being emitted by this flow,
     *  even if other notes remain the same.
     */
    val observeByDefault: Flow<List<Note>>

    /**
     * A [Flow] emitting a list of [Note] objects.
     * This flow observes changes to the underlying data source and emits an updated list
     * whenever a change occurs that affects notes, ordered by their name (alphabetically).
     *
     * The emitted list will always be sorted alphabetically by the [Note]'s name property.
     * Any updates, insertions, or deletions that alter the notes or their names will trigger
     * a new emission from this flow.
     *
     * This is useful for observing a dynamic, sorted list of notes in a reactive manner.
     *
     * @see Note
     */
    val observeByName: Flow<List<Note>>

    /**
     * A [Flow] emitting a list of [Note] objects ordered by their creation date, from oldest to newest.
     *
     * This flow will emit a new list whenever the underlying data source of notes changes.
     * The emitted lists are guaranteed to be sorted such that the oldest note (based on its creation
     * timestamp) will be at the beginning of the list, and the newest note will be at the end.
     *
     * If the underlying data source is empty, this flow will emit an empty list.
     *
     * The implementation details of how the notes are retrieved and ordered are abstracted away,
     * allowing for flexibility in the underlying data storage and retrieval mechanisms.
     */
    val observeByOldest: Flow<List<Note>>

    /**
     * A [Flow] that emits a list of [Note]s sorted by their creation date in descending order (newest first).
     *
     * This flow will emit a new list whenever the underlying data source changes,
     * reflecting the most recent order of notes based on their creation timestamps.
     *
     * Each emission represents a fresh snapshot of the notes in the system, sorted from newest to oldest.
     *
     * The order is determined by comparing the creation timestamps of each [Note].
     *
     * @sample exampleOfHowToConsumeObserveByNewest
     *
     * @see Note
     */
    val observeByNewest: Flow<List<Note>>

    /**
     * A [Flow] emitting a list of [Note] objects ordered by their priority.
     *
     * This flow provides a stream of note lists, where each list is sorted based on the
     * priority of the notes. Higher priority notes will appear earlier in the list.
     * The flow will emit a new list whenever the underlying data changes, reflecting
     * the current state of notes and their priorities.
     *
     * Note: The specific implementation details regarding how priority is determined
     * (e.g., a numeric value, an enum) are not specified by this property, but can
     * be inferred by the context where [Note] is defined.
     *
     * @see Note The data class representing a single note.
     * @see Flow The Kotlin coroutines concept of a flow.
     */
    val observeByPriority: Flow<List<Note>>

    /**
     * A [Flow] of a list of archived [Note]s.
     *
     * This flow emits a new list whenever the set of archived notes changes.
     * It can be used to reactively update a UI or other components that depend on the
     * current state of archived notes.
     *
     * Emitted lists are ordered according to the default note sorting (e.g., by modification date).
     * If you need a different ordering, consider applying a `sortedBy` or `sortedByDescending`
     * operator on the emitted flow.
     *
     * The flow will typically emit an empty list (`listOf()`) if there are no archived notes.
     */
    val observeArchives: Flow<List<Note>>
}