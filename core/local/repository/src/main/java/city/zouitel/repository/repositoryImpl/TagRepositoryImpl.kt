package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.TagRepository
import city.zouitel.repository.datasource.TagDataSource
import city.zouitel.repository.mapper.TagMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Tag as OutTag

/**
 * Implementation of the [TagRepository] interface.
 *
 * This class provides concrete implementations for interacting with the data source
 * for tag-related operations, such as observing, inserting, and deleting tags.
 * It also handles the mapping between the data layer representation (Entities) and the domain layer representation (OutTag).
 *
 * @property dataSource The [TagDataSource] instance used to access and manipulate tag data.
 * @property mapper The [TagMapper] instance used to convert between domain and data layer representations of a tag.
 */
class TagRepositoryImpl(
    private val dataSource: TagDataSource,
    private val mapper: TagMapper
): TagRepository {
    /**
     * A [Flow] that emits a list of all [OutTag]s from the underlying data source.
     *
     * This property observes changes to all tags in the data source and transforms them
     * into a list of domain [OutTag] objects using the provided [mapper].
     *
     * The [Flow] will emit a new list whenever the underlying data source is updated.
     *
     * @see dataSource.observeAll The underlying data source flow.
     * @see mapper.toDomain Mapping function to transform data source tags to domain [OutTag]s.
     */
    override val observeAll: Flow<List<OutTag>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    /**
     * Inserts a [OutTag] domain object into the underlying data source.
     *
     * This function maps the provided [OutTag] to its corresponding data layer representation
     * using the [mapper] and then delegates the insertion operation to the [dataSource].
     *
     * @param tag The [OutTag] domain object to be inserted.
     */
    override suspend fun insert(tag: OutTag) {
        dataSource.insert(mapper.fromDomain(tag))
    }

    /**
     * Deletes an entity from the data source by its unique identifier.
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param id The unique identifier of the entity to be deleted.
     * @throws Exception if any error occurs during the deletion process within the data source. The specific exception depends on the implementation of `dataSource.deleteById()`.
     */
    override suspend fun deleteById(id: Long) {
        dataSource.deleteById(id)
    }
}