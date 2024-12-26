package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Task
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.mapper.TaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Task as OutTask

/**
 * Implementation of the [TaskRepository] interface.
 *
 * This class acts as a bridge between the domain layer ([TaskRepository]) and the data layer ([TaskDataSource]).
 * It handles the conversion of data between domain objects ([Task], [OutTask]) and data source objects.
 *
 * @property dataSource The data source used to interact with the persistent storage of tasks.
 * @property mapper The mapper used to convert between domain objects and data source objects.
 */
class TaskRepositoryImpl(
    private val dataSource: TaskDataSource,
    private val mapper: TaskMapper
): TaskRepository {

    /**
     * A [Flow] that emits a list of [Task] instances representing all tasks in the data source.
     *
     * This observable flow is derived from the underlying data source's [observeAll] flow.
     * Each emission from the data source is transformed from a data model representation to
     * a domain model representation using the provided [mapper].
     *
     * @see dataSource.observeAll The underlying flow emitting data model task lists.
     * @see mapper The mapper used to transform data model tasks to domain model tasks.
     * @return A [Flow] emitting a list of domain model [Task] objects.
     */
    override val observeAll: Flow<List<Task>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    /**
     * Observes a stream of tasks associated with a specific user ID.
     *
     * This function retrieves a flow of task lists from the underlying data source,
     * filtered by the provided user ID (`uid`). It then transforms each list of
     * data source tasks into a list of domain tasks using the provided [mapper].
     *
     * @param uid The unique identifier of the user whose tasks are to be observed.
     * @return A [Flow] emitting lists of [Task] objects associated with the given `uid`.
     *         Emissions occur whenever the underlying data source updates the task list for this user.
     */
    override fun observeByUid(uid: String): Flow<List<Task>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
    }

    /**
     * Inserts an [OutTask] into the data source.
     *
     * This method converts the domain layer [OutTask] object to a data layer
     * representation using the [mapper] and then delegates the insertion
     * operation to the underlying [dataSource].
     *
     * @param task The [OutTask] to be inserted.
     */
    override suspend fun insert(task: OutTask) {
        dataSource.insert(mapper.fromDomain(task))
    }

    /**
     * Updates an existing [Task] in the data source.
     *
     * This function maps the domain [Task] to a data source-specific representation
     * using the [mapper] and then delegates the update operation to the underlying
     * [dataSource].
     *
     * @param task The [Task] object containing the updated information.
     * @throws Exception if an error occurs during the update process in the data source.
     */
    override suspend fun update(task: Task) {
        dataSource.update(mapper.fromDomain(task))
    }

    /**
     * Updates an entity in the data source by its ID.
     *
     * This function delegates the update operation to the underlying [dataSource].
     *
     * @param id The ID of the entity to update.
     * @throws Exception if any error occurs during the update operation within the data source.
     */
    override suspend fun updateById(id: Long) {
        dataSource.updateById(id)
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