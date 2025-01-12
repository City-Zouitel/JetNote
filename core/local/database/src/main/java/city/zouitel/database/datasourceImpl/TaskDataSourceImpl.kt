package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TaskDao
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.repository.datasource.TaskDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Task as OutTask

/**
 * Implementation of the [TaskDataSource] interface.
 *
 * This class is responsible for interacting with the underlying data layer (likely a Room database)
 * through the [TaskDao] and mapping between the data layer models and the repository layer models
 * using the [TaskMapper].
 *
 * @property dao The [TaskDao] used for database operations.
 * @property mapper The [TaskMapper] used for converting between data layer and repository layer models.
 */
class TaskDataSourceImpl(
    private val dao: TaskDao,
    private val mapper: TaskMapper
): TaskDataSource {

    /**
     * A [Flow] that emits a list of all [OutTask]s in the repository.
     *
     * This flow is backed by the underlying [dao.observeAll] flow from the DAO.
     * It transforms the list of database entities emitted by the DAO into a list of
     * repository-level [OutTask] objects using the [mapper].
     *
     * Each emission of the underlying DAO flow will result in a new list of
     * [OutTask]s being emitted by this flow, reflecting any changes in the
     * database.
     */
    override val observeAll: Flow<List<OutTask>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Observes a list of [OutTask] entities associated with a specific user ID (UID).
     *
     * This function retrieves a stream of [OutTask] entities from the underlying data source (DAO)
     * filtered by the provided [uid]. It then transforms the emitted entities into repository-level
     * [OutTask] objects using the specified [mapper].
     *
     * @param uid The unique identifier of the user.
     * @return A [Flow] that emits a list of [OutTask] objects associated with the given [uid].
     *         The list will be empty if no tasks are found for the specified user.
     *         The flow will emit updates whenever the data associated with this uid changes in the underlying data source.
     * @throws Exception Any exception that might occur during data access or mapping will be propagated through the flow.
     */
    override fun observeByUid(uid: String): Flow<List<OutTask>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    /**
     * Inserts an [OutTask] into the data source.
     *
     * This method converts the [OutTask] from the repository layer to the data layer
     * representation using the [mapper] and then inserts it via the [dao].
     *
     * @param task The [OutTask] to be inserted.
     */
    override suspend fun insert(task: OutTask) {
        dao.insert(mapper.fromRepo(task))
    }

    /**
     * Deletes an entity from the data source by its ID.
     *
     * @param id The ID of the entity to deleteById.
     * @throws Exception if an error occurs during the deletion process.
     */
    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    /**
     * Deletes a record from the data source with the specified unique ID (UID).
     *
     * This function suspends the coroutine execution while the deletion operation is performed.
     * It delegates the actual deletion to the underlying [dao] (Data Access Object).
     *
     * @param uid The unique identifier (UID) of the record to be deleted.
     * @throws Exception if there is an error during the deletion process (specific exceptions depend on DAO implementation).
     */
    override suspend fun deleteByUid(uid: String) {
        dao.deleteByUid(uid)
    }
}