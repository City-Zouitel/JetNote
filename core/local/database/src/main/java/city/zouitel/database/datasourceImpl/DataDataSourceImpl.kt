package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.DataDao
import city.zouitel.database.mapper.DataMapper
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.model.Data as OutData

/**
 * Implementation of the [DataDataSource] interface, responsible for interacting with the data storage layer.
 *
 * This class handles the persistence of [OutData] by delegating operations to a [DataDao] and using a [DataMapper]
 * for data conversion between repository and database entities.
 *
 * @property dao The [DataDao] instance used for database operations.
 * @property mapper The [DataMapper] instance used for mapping between repository and database data models.
 */
class DataDataSourceImpl(
    private val dao: DataDao,
    private val mapper: DataMapper
): DataDataSource {
    /**
     * Inserts the provided [OutData] into the data source.
     *
     * This function maps the [OutData] from the repository layer to the data layer's internal
     * representation using the [mapper] and then uses the [dao] to perform the insertion.
     * This operation is performed within the context of the dispatcher specified by `coroutineDispatcher` (e.g. IO dispatcher).
     *
     * @param data The [OutData] object to be inserted.
     */
    override suspend fun insert(data: OutData) {
        dao.insert(mapper.fromRepo(data))
    }

    /**
     * Archives a data entry with the given unique identifier (UID).
     *
     * This operation marks the data entry as archived in the underlying data source.
     * Depending on the implementation of the data source, this might involve setting a flag,
     * moving the entry to a separate archive table, or deleting it after some period.
     *
     * @param uid The unique identifier of the data entry to archive.
     * @throws Exception if an error occurs while archiving the entry in the data source.
     *          The specific exception type depends on the implementation of the DAO.
     */
    override suspend fun archive(uid: String) {
        dao.archive(uid)
    }

    /**
     * Rolls back any pending changes or operations associated with the given [uid].
     *
     * This function is a suspend function, meaning it can be paused and resumed. It is typically used
     * for operations that might take some time to complete, such as interacting with a database or
     * network. In this specific context it rollbacks changes for a user (identified by uid).
     *
     * **Note:** The exact behavior of "rollback" depends on the implementation of the underlying [dao.rollback] function.
     * It's expected that this operation will undo any modifications previously made for the given [uid]
     * that haven't yet been finalized (e.g., committed to a database).
     *
     * @param uid The unique identifier of the user or entity for whom the changes should be rolled back.
     * @throws Exception if an error occurs during the rollback process. The specific exception thrown
     *                   will depend on the implementation of `dao.rollback`.
     */
    override suspend fun rollback(uid: String) {
        dao.rollback(uid)
    }

    /**
     * Deletes a record from the data source associated with the specified unique identifier (UID).
     *
     * This function is a suspend function, meaning it can be paused and resumed without blocking the calling thread.
     * It delegates the deletion operation to the underlying DAO (Data Access Object).
     *
     * @param uid The unique identifier of the record to be deleted.
     *
     * @throws Exception if any error occurs during the deletion process (e.g., database error).
     */
    override suspend fun delete(uid: String) {
        dao.delete(uid)
    }

    /**
     * Erases all data from the underlying data source.
     *
     * This function clears all entries managed by this repository.
     * After calling this, the data source will be empty.
     *
     * This is a suspending function, meaning it should be called from a coroutine or another suspending function.
     */
    override suspend fun erase() {
        dao.erase()
    }
}
