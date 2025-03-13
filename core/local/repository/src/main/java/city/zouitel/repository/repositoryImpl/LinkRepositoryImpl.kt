package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.LinkRepository
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.mapper.LinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Link as OutLink

/**
 * Implementation of the [LinkRepository] interface.
 *
 * This class is responsible for managing link data by interacting with a [LinkDataSource]
 * and mapping between data transfer objects (DTOs) and domain objects using a [LinkMapper].
 *
 * @property dataSource The [LinkDataSource] used to access and manipulate the underlying data.
 * @property mapper The [LinkMapper] used to convert between [OutLink] domain objects and their data layer representations.
 */
class LinkRepositoryImpl(
    private val dataSource: LinkDataSource,
    private val mapper: LinkMapper
): LinkRepository {
    /**
     * A [Flow] of a list of [OutLink] representing all external links in the data source.
     *
     * This observable flow emits a new list of [OutLink] whenever the underlying data source's data changes.
     * Each emitted list is a mapped representation of the raw data from the [dataSource], transformed
     * into the domain model ([OutLink]) using the [mapper].
     *
     * @see dataSource.observeAll The underlying flow from the data source.
     * @see mapper.toDomain The mapper used to transform the data source's output to the domain model.
     */
    override val observeAll: Flow<List<OutLink>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    /**
     * Observes a stream of [OutLink] lists associated with a specific user ID (UID).
     *
     * This function retrieves a flow of [OutLink] entities from the underlying data source
     * corresponding to the provided [uid]. It then transforms these entities from their
     * data source representation to the domain model using the [mapper].
     *
     * @param uid The unique identifier of the user.
     * @return A [Flow] that emits a list of [OutLink] objects whenever changes occur
     *         in the data source for the specified user.
     */
    override suspend fun observeByUid(uid: String): Flow<List<OutLink>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
    }

    /**
     * Inserts an [OutLink] into the data source.
     *
     * This method converts the domain [OutLink] object to its data source representation
     * using the [mapper] and then delegates the insertion to the [dataSource].
     *
     * @param link The [OutLink] object to be inserted.
     */
    override suspend fun insert(link: OutLink) {
        dataSource.insert(mapper.fromDomain(link))
    }

    /**
     * Deletes a record from the data source by its ID.
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     * If a record with the specified [id] exists, it will be removed from the data source.
     * If no record with the given [id] exists, no changes will be made.
     *
     * @param id The unique identifier of the record to delete.
     * @throws Exception If any error occurs during the deletion process. (Add specific exceptions if known, e.g., DatabaseException)
     */
    override suspend fun delete(id: Int) {
        dataSource.delete(id)
    }

    /**
     * Deletes all draft messages from the data source.
     *
     * This function clears the storage of any messages that were saved as drafts,
     * meaning they were created but not yet sent.  After calling this function,
     * no draft messages should remain in the underlying data source.
     */
    override suspend fun deleteDrafts() {
        dataSource.deleteDrafts()
    }
}