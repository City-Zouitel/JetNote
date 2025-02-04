package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteDao
import city.zouitel.database.mapper.NoteMapper
import city.zouitel.repository.datasource.NoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Note as OutNote

/**
 * Implementation of the [NoteDataSource] interface.
 *
 * This class provides access to note data from the underlying data access object ([NoteDao])
 * and transforms the data into the repository's domain model ([OutNote]) using a [NoteMapper].
 *
 * @property dao The [NoteDao] used to access and manipulate note data in the underlying data source.
 * @property mapper The [NoteMapper] used to transform data between the data source's model and the repository's domain model.
 */
class NoteDataSourceImpl(
    private val dao: NoteDao,
    private val mapper: NoteMapper
): NoteDataSource {
    /**
     * A flow emitting a list of [OutNote] objects.
     *
     * This flow observes changes in the underlying data source (likely a database)
     * and emits the latest list of notes whenever a change occurs.  The emitted
     * list is transformed from the data source's native representation to the
     * repository's [OutNote] type using the [mapper].
     *
     * The default behavior is to observe all notes without any specific filtering or ordering.
     * This is achieved by delegating to [dao.observeByDefault()] and mapping the resulting data.
     *
     * @see [dao.observeByDefault] For details about how the data source observes changes.
     * @see [mapper.toRepo] For details about the data transformation.
     *
     * @return A [Flow] that emits a list of [OutNote] objects.
     */
    override val observeByDefault: Flow<List<OutNote>>
        get() = dao.observeByDefault().map { mapper.toRepo(it) }

    /**
     * A flow of a list of [OutNote] objects, sorted by name.
     *
     * This flow emits a new list whenever the underlying data in the DAO changes.
     * It retrieves the data from the DAO ([dao.observeByName()]) and then maps it
     * to the repository's [OutNote] model using the provided [mapper].
     *
     * @see dao.observeByName()
     * @see mapper.toRepo()
     */
    override val observeByName: Flow<List<OutNote>>
        get() = dao.observeByName().map { mapper.toRepo(it) }

    /**
     * A [Flow] of [List] of [OutNote] ordered by their creation date, from oldest to newest.
     *
     * This property provides a stream of notes fetched from the underlying data source ([dao]) and transformed into
     * the repository's [OutNote] domain objects using the provided [mapper]. The notes are sorted in ascending order
     * based on their creation timestamps, meaning the oldest notes appear first in the list.
     *
     * @return A [Flow] emitting a [List] of [OutNote] sorted by their creation date, from oldest to newest.
     *
     * @see dao.observeByOldest
     * @see mapper.toRepo
     */
    override val observeByOldest: Flow<List<OutNote>>
        get() = dao.observeByOldest().map { mapper.toRepo(it) }

    /**
     * A flow that emits a list of [OutNote] objects sorted by their creation/modification date in descending order (newest first).
     *
     * This flow observes changes in the underlying data source (typically a database via the DAO) and emits
     * an updated list of [OutNote] objects whenever changes occur. The data retrieved from the DAO is then
     * transformed using a mapper to convert it into the [OutNote] repository model.
     *
     * @return A [Flow] emitting a list of [OutNote] sorted by newest first.
     */
    override val observeByNewest: Flow<List<OutNote>>
        get() = dao.observeByNewest().map { mapper.toRepo(it) }

    /**
     * A [Flow] of a list of [OutNote] objects, ordered by their priority.
     *
     * This flow emits a new list whenever the underlying data source (the DAO) updates its data related to notes' priorities.
     * The raw data from the DAO is transformed into [OutNote] objects using the provided [mapper].
     *
     * @return A [Flow] emitting a list of [OutNote] ordered by priority.
     * @see [NoteDao.observeByPriority] for the underlying data source.
     * @see [mapper] for the data transformation.
     */
    override val observeByPriority: Flow<List<OutNote>>
        get() = dao.observeByPriority().map { mapper.toRepo(it) }

    /**
     * A flow of lists of notes that have been removed (archived).
     *
     * This property provides a reactive stream of updates representing the notes that have been moved
     * to the archive. Each emission from this flow is a new list containing the currently archived notes.
     * The underlying implementation uses the DAO to observe changes in the archive table and maps the
     * resulting entities to repository-level notes.
     *
     * @return A [Flow] emitting a [List] of [OutNote] instances representing the archived notes.
     */
    override val observeArchives: Flow<List<OutNote>>
        get() = dao.observeArchives().map { mapper.toRepo(it) }
}