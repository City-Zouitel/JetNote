package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.LinkDao
import city.zouitel.database.mapper.LinkMapper
import city.zouitel.repository.datasource.LinkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Link as OutLink

/**
 * Implementation of the [LinkDataSource] interface.
 *
 * This class is responsible for interacting with the data layer (database) for link-related operations.
 * It utilizes a [LinkDao] for database access and a [LinkMapper] for converting between data models.
 *
 * @property dao The [LinkDao] instance used for database interactions.
 * @property mapper The [LinkMapper] instance used for data model conversions.
 */
class LinkDataSourceImpl(
    private val dao: LinkDao,
    private val mapper: LinkMapper
): LinkDataSource {
    /**
     * A [Flow] that emits a list of all [OutLink] entities stored in the data source.
     *
     * This observable flow provides a stream of data reflecting the current state of all [OutLink]
     * records in the underlying storage. Any changes to the [OutLink] entities will trigger a new
     * emission with the updated list.
     *
     * The raw data from the DAO ([dao.observeAll]) is transformed using the [mapper] to map
     * the data source entities to the repository-level [OutLink] entities.
     *
     * @see OutLink
     * @see dao.observeAll
     * @see mapper
     * @return A [Flow] emitting lists of [OutLink].
     */
    override val observeAll: Flow<List<OutLink>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Observes a list of [OutLink] entities associated with a specific user ID (UID).
     *
     * This function retrieves a reactive stream of [OutLink] data from the underlying data access object (DAO)
     * for a given user ID. It then transforms the DAO's output into a list of repository-level [OutLink] objects using the provided mapper.
     *
     * @param uid The unique identifier of the user for whom to observe the out links.
     * @return A [Flow] emitting a list of [OutLink] entities associated with the specified `uid`.
     * The flow emits a new list whenever the underlying data changes.
     */
    override suspend fun observeByUid(uid: String): Flow<List<OutLink>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    /**
     * Inserts an [OutLink] into the data source.
     *
     * This method converts the provided [OutLink] (likely a domain/repository model) into a
     * corresponding data layer entity before inserting it via the DAO.
     *
     * @param link The [OutLink] to be inserted. This is typically a model from the domain or repository layer.
     */
    override suspend fun insert(link: OutLink) {
        dao.insert(mapper.fromRepo(link))
    }

    /**
     * Deletes an entity from the data source by its ID.
     *
     * This function suspends execution and performs the deletion operation asynchronously.
     * It typically interacts with a data access object (DAO) to remove the entity.
     *
     * @param id The ID of the entity to delete.
     */
    override suspend fun delete(id: Int) {
        dao.delete(id)
    }

    /**
     * Deletes all drafts from the underlying data source.
     *
     * This function is a suspend function, meaning it can be safely called
     * within a coroutine. It delegates the actual deletion operation to the
     * data access object (DAO).

     * @throws Exception if an error occurs during the deletion process. The
     *         specific type of exception depends on the implementation of
     *         the DAO.
     */
    override suspend fun deleteDrafts() {
        dao.deleteDrafts()
    }
}