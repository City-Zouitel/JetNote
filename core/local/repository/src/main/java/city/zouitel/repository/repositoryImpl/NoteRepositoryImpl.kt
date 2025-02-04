package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Note
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.repository.datasource.NoteDataSource
import city.zouitel.repository.mapper.NoteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the [NoteRepository] interface.
 *
 * This class provides access to notes from a data source ([NoteDataSource]) and maps them to the domain
 * model using a [NoteMapper]. It exposes several Flows that allow observing changes in the note list
 * based on different sorting and filtering criteria.
 *
 * @property dataSource The data source for retrieving note data.
 * @property mapper The mapper for converting data layer note objects to domain layer note objects.
 */
class NoteRepositoryImpl(
    private val dataSource: NoteDataSource,
    private val mapper: NoteMapper
): NoteRepository {
    /**
     * A [Flow] of a list of [Note] entities that are observed with default sorting and filtering.
     *
     * This property provides a reactive stream of [Note] objects derived from the underlying
     * data source's default observation. Any changes in the data source that match the default
     * criteria will be reflected in the emitted list.
     *
     * The emitted lists are transformed from the data layer representation to the domain
     * layer representation using the [mapper].
     *
     * @see [dataSource.observeByDefault] The underlying data source's default observation.
     * @see [mapper.toDomain] The mapper used to transform data layer entities to domain entities.
     */
    override val observeByDefault: Flow<List<Note>>
        get() = dataSource.observeByDefault.map { mapper.toDomain(it) }

    /**
     * A flow of a list of [Note] objects, ordered by their name.
     *
     * This property provides a reactive stream of [Note] lists. Each emission represents the current
     * state of notes in the underlying data source, sorted alphabetically by their name. The emitted
     * data is transformed from the data layer representation to the domain layer representation using
     * the [mapper].
     *
     * The flow emits a new list whenever the underlying data source ([dataSource.observeByName])
     * changes and the changes affect the order of notes by name.
     *
     * @see dataSource.observeByName The underlying flow of data layer note representations.
     * @see mapper The mapper used to convert data layer objects to domain layer objects.
     * @return A [Flow] emitting lists of [Note] objects sorted alphabetically by their name.
     */
    override val observeByName: Flow<List<Note>>
        get() = dataSource.observeByName.map { mapper.toDomain(it) }

    /**
     * A [Flow] of [List<Note>] representing notes observed in order from oldest to newest.
     *
     * This property delegates to the underlying [dataSource] to observe the notes,
     * then maps the data to domain objects using the [mapper].
     *
     * The order of notes emitted by this flow is guaranteed to be from the oldest
     * created to the newest created, based on the underlying data source's
     * ordering.
     *
     * @see dataSource.observeByOldest
     * @see mapper.toDomain
     */
    override val observeByOldest: Flow<List<Note>>
        get() = dataSource.observeByOldest.map { mapper.toDomain(it) }

    /**
     * A [Flow] of a list of [Note]s ordered by newest first.
     *
     * This flow observes changes in the underlying data source's notes ordered by newest,
     * maps them to the domain layer's [Note] representation using the provided [mapper],
     * and emits the updated list of [Note]s.
     *
     * This provides a reactive stream of notes, where any changes to the underlying data source
     * (e.g., new note added, note updated, note deleted) will trigger a new emission with
     * the updated list of notes.
     *
     * The order is determined by the data source's `observeByNewest` method, which is expected
     * to return notes sorted by a timestamp or another relevant ordering criteria.
     *
     * @see dataSource.observeByNewest
     * @see mapper.toDomain
     * @return A [Flow] emitting lists of [Note]s, ordered from newest to oldest.
     */
    override val observeByNewest: Flow<List<Note>>
        get() = dataSource.observeByNewest.map { mapper.toDomain(it) }

    /**
     * A [Flow] of [List<Note>] representing notes observed in order of their priority.
     *
     * This flow emits a new list of [Note] objects whenever the underlying data source's
     * priority-based observation changes. Each emitted list is a result of mapping
     * the data source's output to the domain model using the [mapper].
     *
     * The order of the notes within each emitted list reflects the priority, with higher priority
     * notes appearing earlier in the list.
     *
     * @see dataSource.observeByPriority The underlying data source's flow of notes ordered by priority.
     * @see mapper.toDomain Maps the data source's representation of notes to the domain's [Note] model.
     */
    override val observeByPriority: Flow<List<Note>>
        get() = dataSource.observeByPriority.map { mapper.toDomain(it) }

    /**
     * A [Flow] that emits a list of archived [Note] objects.
     *
     * This property provides a stream of updates for archived notes.
     * It retrieves the archived notes from the underlying data source and maps them to domain [Note] objects.
     *
     * The stream emits whenever the underlying data source's archived notes change.
     * Each emitted list contains the latest state of archived notes.
     *
     * @see dataSource.observeArchives for the underlying data source stream.
     * @see mapper.toDomain to convert the data layer objects to domain objects.
     *
     * @return A [Flow] emitting lists of [Note] representing archived notes.
     */
    override val observeArchives: Flow<List<Note>>
        get() = dataSource.observeArchives.map { mapper.toDomain(it) }
}