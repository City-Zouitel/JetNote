package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Data
import city.zouitel.domain.repository.DataRepository
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.mapper.DataMapper

/**
 * Implementation of the [DataRepository] interface.
 *
 * This class handles data operations by delegating them to the [DataDataSource]
 * and utilizing the [DataMapper] for domain-to-data model transformations.
 *
 * @property dataSource The data source responsible for interacting with the underlying data store.
 * @property mapper The mapper responsible for converting between [Data] domain objects and data layer objects.
 */
class DataRepositoryImpl(
    private val dataSource: DataDataSource,
    private val mapper: DataMapper
): DataRepository {
    /**
     * Inserts a [Data] object into the data source.
     *
     * This method maps the domain [Data] object to a data source-specific representation
     * using the provided [mapper] and then delegates the insertion to the [dataSource].
     *
     * @param data The [Data] object to be inserted.
     */
    override suspend fun insert(data: Data) {
        dataSource.insert(mapper.fromDomain(data))
    }

    /**
     * Archives a record with the specified unique identifier (UID).
     *
     * This function delegates the archiving operation to the underlying [dataSource].
     * Archiving typically involves marking a record as inactive or moving it to an archive section,
     * depending on the implementation of the [dataSource].
     *
     * @param uid The unique identifier of the record to be archived.
     * @throws Exception if any error occurs during the archiving process within the [dataSource]. The specific exception type
     * will depend on the [dataSource] implementation.
     */
    override suspend fun archive(uid: String) {
        dataSource.archive(uid)
    }

    /**
     * Rolls back a transaction associated with the given user ID.
     *
     * This function attempts to undo any changes made during a transaction
     * identified by the provided [uid]. It delegates the rollback operation
     * to the underlying [dataSource].
     *
     * @param uid The unique identifier of the transaction to roll back.
     * @throws Exception if any error occurs during the rollback process. The specific
     *         exception type depends on the implementation of [dataSource.rollback].
     */
    override suspend fun rollback(uid: String) {
        dataSource.rollback(uid)
    }

    /**
     * Deletes a record from the data source associated with the given unique identifier (UID).
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param uid The unique identifier of the record to be deleted.
     * @throws Exception If any error occurs during the deletion process in the data source.
     */
    override suspend fun delete(uid: String) {
        dataSource.delete(uid)
    }

    /**
     * Erases all data managed by this repository.
     *
     * This function delegates the erasure operation to the underlying [dataSource].
     * It's important to note that this is a destructive operation, and all data will be permanently removed.
     *
     * @see [DataSource.erase] for details on the data source's erasure implementation.
     *
     * @throws [Exception] if any error occurs during the data erasure process in the [dataSource].
     */
    override suspend fun erase() {
        dataSource.erase()
    }
}