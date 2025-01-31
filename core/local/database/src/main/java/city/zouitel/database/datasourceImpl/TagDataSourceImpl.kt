package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TagDao
import city.zouitel.database.mapper.TagMapper
import city.zouitel.repository.datasource.TagDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Tag as OutTag

/**
 * Implementation of the [TagDataSource] interface.
 *
 * This class acts as a bridge between the data layer (database) and the repository layer
 * for tag-related operations. It utilizes a [TagDao] for direct database interactions
 * and a [TagMapper] for transforming data between the database entities and the repository
 * domain models.
 *
 * @property dao The [TagDao] instance used for interacting with the tag database table.
 * @property mapper The [TagMapper] instance used for mapping between database entities and repository models.
 */
class TagDataSourceImpl(
    private val dao: TagDao,
    private val mapper: TagMapper
): TagDataSource {
    /**
     * A [Flow] that emits a list of all [OutTag]s in the repository.
     *
     * This flow is backed by the underlying [dao.observeAll] flow from the DAO,
     * which emits whenever the underlying data in the database changes.
     * Each emitted list of database entities is transformed to a list of [OutTag]s
     * using the provided [mapper].
     *
     * @see dao.observeAll The underlying flow from the DAO.
     * @see mapper The mapper used to transform database entities to [OutTag]s.
     */
    override val observeAll: Flow<List<OutTag>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Inserts an [OutTag] into the data source.
     *
     * This method converts the provided [OutTag] (likely a domain/repository layer object)
     * into an appropriate entity for the data source (e.g., a database entity) using the
     * [mapper] and then uses the [dao] to perform the insertion.
     *
     * This is a suspend function, meaning it can be called from a coroutine and will
     * suspend execution until the insertion operation is complete.
     *
     * @param tag The [OutTag] to be inserted.
     */
    override suspend fun insert(tag: OutTag) {
        dao.insert(mapper.fromRepo(tag))
    }

    /**
     * Deletes an entity from the data source by its ID.
     *
     * @param id The ID of the entity to delete.
     * @throws Exception if an error occurs during the deletion process.
     */
    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}