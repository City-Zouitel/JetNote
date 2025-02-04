package city.zouitel.domain.usecase

import city.zouitel.domain.repository.NoteRepository

/**
 * Sealed class representing different use cases for observing notes.
 *
 * This class encapsulates the various ways in which notes can be observed,
 * such as by default, by name, by oldest, by newest, by priority, and archived.
 * Each subclass represents a specific observation strategy and delegates
 * the actual data retrieval to the [NoteRepository].
 */
sealed class NoteUseCase {
    /**
     * ObserveByDefault use case.
     *
     * This use case is responsible for observing the default set of notes from the [NoteRepository].
     * It leverages the underlying repository's observation mechanism to provide a reactive stream of
     * the default notes.
     *
     * @property repo The [NoteRepository] instance responsible for providing the note data.
     */
    class ObserveByDefault(private val repo: NoteRepository): NoteUseCase() {
        operator fun invoke() = repo.observeByDefault
    }

    /**
     * [ObserveByName] is a [NoteUseCase] that provides a [Flow] of lists of [Note]s
     * ordered by their names. It uses the underlying [NoteRepository] to fetch and observe
     * these notes.
     *
     * This use case is responsible for retrieving the stream of notes sorted alphabetically
     * by name. This allows UI components to reactively update as the list of notes
     * changes in the repository.
     *
     * @property repos The [NoteRepository] responsible for providing access to the note data.
     */
    class ObserveByName(private val repos: NoteRepository): NoteUseCase() {
        operator fun invoke() = repos.observeByName
    }

    /**
     * ObserveByOldest is a use case responsible for observing notes from the repository
     * ordered by their creation date, starting with the oldest.
     *
     * This use case provides a [Flow] of [Note] objects emitted by the underlying
     * [NoteRepository.observeByOldest] function.
     *
     * @property repo The [NoteRepository] instance used to fetch and observe notes.
     * @constructor Creates an instance of [ObserveByOldest] with the specified [NoteRepository].
     */
    class ObserveByOldest(private val repo: NoteRepository): NoteUseCase() {
        operator fun invoke() = repo.observeByOldest
    }

    /**
     * [ObserveByNewest] is a [NoteUseCase] that provides a [Flow] of [Note]s ordered by their creation date in descending order (newest first).
     *
     * This use case delegates the actual observation to the underlying [NoteRepository].
     *
     * @property repo The [NoteRepository] instance responsible for data access.
     */
    class ObserveByNewest(private val repo: NoteRepository): NoteUseCase() {
        operator fun invoke() = repo.observeByNewest
    }

    /**
     * ObserveByPriority is a use case that retrieves a stream of notes ordered by their priority.
     *
     * This use case delegates the actual data retrieval to the underlying [NoteRepository].
     *
     * @property repo The [NoteRepository] instance used to access the notes.
     */
    class ObserveByPriority(private val repo: NoteRepository): NoteUseCase() {
        operator fun invoke() = repo.observeByPriority
    }

    /**
     * `ObserveArchives` is a use case that provides a stream of archived notes.
     *
     * This use case leverages the [NoteRepository] to retrieve a `Flow` representing
     * the list of notes that are currently archived. It is designed to be used for observing
     * changes to the archived notes in real-time.
     *
     * @property repo The [NoteRepository] instance used to access the data layer for notes.
     * @constructor Creates an instance of [ObserveArchives] with the specified [NoteRepository].
     */
    class ObserveArchives(private val repo: NoteRepository): NoteUseCase() {
        operator fun invoke() = repo.observeArchives
    }
}
