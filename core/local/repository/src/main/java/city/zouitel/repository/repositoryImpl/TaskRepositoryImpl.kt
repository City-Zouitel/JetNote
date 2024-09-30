package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Task as OutTask
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.mapper.TaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dataSource: TaskDataSource,
    private val mapper: TaskMapper
): TaskRepository {
    override val getAllTaskItems: Flow<List<OutTask>>
        get() = dataSource.getAllTaskItems.map { tasks -> mapper.toDomain(tasks) }

    override suspend fun addTaskItem(task: OutTask) {
        dataSource.addTaskItem(mapper.fromDomain(task))
    }

    override suspend fun updateTaskItem(task: OutTask) {
        dataSource.updateTaskItem(mapper.fromDomain(task))
    }

    override suspend fun deleteTaskItem(task: OutTask) {
        dataSource.deleteTaskItem(mapper.fromDomain(task))
    }
}