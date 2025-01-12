package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Media
import city.zouitel.domain.repository.MediaRepository
import city.zouitel.repository.datasource.MediaDataSource
import city.zouitel.repository.mapper.MediaMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the [MediaRepository] interface.
 *
 * This class provides concrete implementations for interacting with media data,
 * utilizing a [MediaDataSource] for data access and a [MediaMapper] for data transformation
 * between the data layer and the domain layer.
 *
 * @property dataSource The data source responsible for interacting with the underlying media data store.
 * @property mapper The mapper responsible for transforming data between the data layer representation and the domain layer representation of [Media].
 */
class MediaRepoImpl(
    private val dataSource: MediaDataSource,
    private val mapper: MediaMapper
): MediaRepository {
    /**
     * A [Flow] that emits a list of [Media] items whenever the underlying data source changes.
     *
     * This property provides a reactive stream of all available media items, transformed into the domain model.
     * It delegates the observation of data changes to the underlying [dataSource] and maps the retrieved data
     * from the data source representation to the domain representation using the provided [mapper].
     *
     * @see dataSource.observeAll The underlying data source flow that emits lists of media data.
     * @see mapper The mapper responsible for converting data source media representations to domain [Media] objects.
     *
     * @return A [Flow] emitting lists of [Media] objects, representing the current state of all available media.
     */
    override val observeAll: Flow<List<Media>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    /**
     * Observes a list of Media objects associated with a specific user ID (UID).
     *
     * This function retrieves a stream of `Media` objects from the underlying data source,
     * filtering them by the provided `uid`. The retrieved data is then transformed
     * into the domain layer's `Media` representation using the provided `mapper`.
     *
     * @param uid The unique identifier of the user whose media is being observed.
     * @return A [Flow] emitting a list of [Media] objects associated with the specified `uid`.
     *         The flow emits a new list whenever the underlying data changes.
     * @throws Exception if an error occurs during data retrieval or mapping.
     */
    override suspend fun observeByUid(uid: String): Flow<List<Media>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
}

    /**
     * Inserts a [Media] object into the underlying data source.
     *
     * This function takes a domain [Media] object, maps it to a data source-specific representation using the
     * [mapper], and then delegates the insertion operation to the [dataSource].
     *
     * @param media The [Media] object to be inserted. This object represents the media in the application's domain model.
     * @throws Exception if any error occurs during the mapping or insertion process. (Consider specifying more specific exceptions if possible)
     * @see dataSource
     * @see mapper
     */
    override suspend fun insert(media: Media) {
        dataSource.insert(mapper.fromDomain(media))
    }

    /**
     * Deletes an entity from the data source by its ID.
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param id The ID of the entity to delete.
     * @throws SomeExceptionType If there's an error during deletion (if your DataSource can throw exceptions).
     *         Consider specifying which exceptions your dataSource can potentially throw.
     *         Remove this if your DataSource doesn't throw exceptions.
     * @see DataSource.deleteById For details on the underlying deletion implementation.
     */
    override suspend fun deleteById(id: Long) {
        dataSource.deleteById(id)
    }

    /**
     * Deletes a record from the data source using the provided unique identifier (UID).
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param uid The unique identifier of the record to be deleted.
     */
    override suspend fun deleteByUid(uid: String) {
        dataSource.deleteByUid(uid)
    }
}