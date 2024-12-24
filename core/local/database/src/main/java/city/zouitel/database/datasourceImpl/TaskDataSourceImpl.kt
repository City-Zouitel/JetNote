package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TaskDao
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Task as OutTask

class TaskDataSourceImpl(
    private val dao: TaskDao,
    private val mapper: TaskMapper
): TaskDataSource {
    override val observeAll: Flow<List<Task>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    override fun observeByUid(uid: String): Flow<List<Task>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    override suspend fun insert(task: OutTask) {
        dao.insert(mapper.fromRepo(task))
    }

    override suspend fun updateById(id: Long) {
        dao.updateById(id)
    }

    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}