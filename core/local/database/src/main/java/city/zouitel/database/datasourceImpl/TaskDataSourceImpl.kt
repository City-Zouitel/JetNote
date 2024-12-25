package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TaskDao
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.model.Task
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
     * A [Flow] that emits a list of [Task] objects representing all tasks in the data source.
     *
     * This observable flow is derived from the underlying data access object (DAO)'s `observeAll`
     * method, which provides a flow of data layer [Task] objects.  This property then
     * maps that flow into a flow of domain layer Task objects using the provided [mapper].
     *
     * Each emission represents a snapshot of the current state of all tasks in the data source.
     * Any changes to the tasks in the data source will result in a new emission containing
     * the updated list.
     *
     * @see dao.observeAll for the underlying flow of data layer entities.
     * @see mapper.toRepo for the transformation logic from data layer entities to domain objects.
     */
    override val observeAll: Flow<List<Task>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Observes a list of [Task] entities associated with a specific user ID.
     *
     * This function retrieves a stream of tasks from the data source (presumably a database)
     * that are linked to the provided `uid`. It then transforms the data source's entities
     * into repository-level [Task] objects using a mapper.
     *
     * @param uid The unique identifier of the user whose tasks are to be observed.
     * @return A [Flow] emitting a list of [Task] objects. Each emitted list represents
     *         the current set of tasks associated with the user ID, reflecting changes
     *         in the underlying data source.
     * @throws Exception If any error happens during data fetching or mapping.
     */
    override fun observeByUid(uid: String): Flow<List<Task>> {
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
     * Updates a record in the underlying data source by its ID.
     *
     * This function utilizes a suspending call to interact with the data access object (DAO).
     * It delegates the update operation to the [dao] object's `updateById` method.
     *
     * @param id The ID of the record to update.
     * @throws Exception if an error occurs during the update process.
     */
    override suspend fun updateById(id: Long) {
        dao.updateById(id)
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