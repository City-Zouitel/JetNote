package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Task
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.mapper.TaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Task as OutTask

class TaskRepositoryImpl(
    private val dataSource: TaskDataSource,
    private val mapper: TaskMapper
): TaskRepository {
    override val observeAll: Flow<List<Task>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    override fun observeByUid(uid: String): Flow<List<Task>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
    }

    override suspend fun insert(task: OutTask) {
        dataSource.insert(mapper.fromDomain(task))
    }

    override suspend fun updateById(id: Long) {
        dataSource.updateById(id)
    }

    override suspend fun deleteById(id: Long) {
        dataSource.deleteById(id)
    }
}