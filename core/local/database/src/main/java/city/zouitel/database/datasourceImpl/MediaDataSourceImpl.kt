package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.MediaDao
import city.zouitel.database.mapper.MediaMapper
import city.zouitel.repository.datasource.MediaDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Media as OutMedia

/**
 * Implementation of the [MediaDataSource] interface.
 *
 * This class is responsible for interacting with the local data layer (database)
 * to perform CRUD operations on media entities. It uses a [MediaDao] for database access
 * and a [MediaMapper] for mapping between database entities and repository entities.
 *
 * @property dao The [MediaDao] instance used for database interactions.
 * @property mapper The [MediaMapper] instance used for mapping between data and repository models.
 */
class MediaDataSourceImpl(
    private val dao: MediaDao,
    private val mapper: MediaMapper
): MediaDataSource {
    /**
     * A [Flow] that emits a list of all [OutMedia] entities from the underlying data source.
     *
     * This flow observes changes to all media entities in the data access object (DAO) and
     * transforms them into a list of [OutMedia] objects using the provided [mapper].
     * Each emission represents the current state of all media items in the persistent store.
     *
     * @see [OutMedia] The representation of media used in the repository layer.
     * @see [dao] The Data Access Object responsible for interacting with the underlying data source.
     * @see [mapper] The mapper used to transform data entities from the DAO into [OutMedia] objects.
     * @see Flow Kotlin's asynchronous stream of data.
     *
     * @return A [Flow] that emits a list of [OutMedia] whenever the underlying data source changes.
     */
    override val observeAll: Flow<List<OutMedia>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Observes a list of media items associated with a specific user ID (UID).
     *
     * This function retrieves a stream of `OutMedia` objects from the data source (likely a database)
     * that are associated with the given [uid]. It leverages a [dao] (Data Access Object) to
     * interact with the underlying data and a [mapper] to transform the data from the
     * DAO's internal representation to the `OutMedia` domain object.
     *
     * @param uid The unique identifier of the user whose media items are to be observed.
     * @return A [Flow] that emits a list of [OutMedia] objects whenever the underlying data changes
     *         for the specified user. The list will be empty if no media items are associated
     *         with the provided `uid`.
     * @throws Exception Any exception thrown by the DAO or the mapper.
     */
    override suspend fun observeByUid(uid: String): Flow<List<OutMedia>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    /**
     * Inserts a [OutMedia] object into the database.
     *
     * This method takes an [OutMedia] object, maps it to the corresponding database entity using the [mapper],
     * and then inserts the entity into the database via the [dao].
     *
     * @param media The [OutMedia] object to be inserted.
     */
    override suspend fun insert(media: OutMedia) {
        dao.insert(mapper.fromRepo(media))
    }

    /**
     * Deletes an entity from the data source by its ID.
     *
     * @param id The ID of the entity to delete.
     * @throws Exception if there's an error during the deletion process. This should be replaced with a more specific exception if applicable.
     */
    override suspend fun deleteById(id: Long) {
        dao.delete(id)
    }

    /**
     * Deletes all drafts from the underlying data source.
     *
     * This function is a suspend function, meaning it can be safely called
     * within a coroutine. It delegates the actual deletion operation to the
     * data access object (DAO).
     *
     * @throws Exception if an error occurs during the deletion process. The
     *         specific type of exception depends on the implementation of
     *         the DAO.
     */
    override suspend fun deleteDrafts() {
        dao.deleteDrafts()
    }

    /**
     * Retrieves a list of drafts from the data source.
     *
     * This function fetches all stored drafts, which are represented as a list of Strings.
     * The specific format and content of these strings are determined by the underlying
     * data storage mechanism (e.g., a database table with a text column).
     *
     * This is a suspend function, meaning it should be called within a coroutine scope
     * or another suspend function. This allows for efficient handling of potential
     * I/O operations, such as database queries, without blocking the main thread.
     *
     * @return A [List] of [String] representing the drafts. An empty list is returned if no drafts are found.
     *
     * @throws Exception If any error occurs during the retrieval process. The specific type
     *                   of exception depends on the underlying data source and the nature of
     *                   the error.
     */
    override suspend fun getDrafts(): List<String> {
        return dao.getDrafts()
    }
}