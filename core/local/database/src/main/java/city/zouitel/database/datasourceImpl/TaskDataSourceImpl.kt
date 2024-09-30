package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TaskDao
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.model.Task as OutTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskDataSourceImpl(
    private val dao: TaskDao,
    private val mapper: TaskMapper
): TaskDataSource {
    override val getAllTaskItems: Flow<List<OutTask>>
        get() = dao.getAllTaskItems().map { tasks -> mapper.toRepo(tasks) }

    override suspend fun addTaskItem(task: OutTask) {
        dao.addTaskItem(mapper.fromRepo(task))
    }

    override suspend fun updateTaskItem(task: OutTask) {
        dao.updateTaskItem(mapper.fromRepo(task))
    }

    override suspend fun deleteTaskItem(task: OutTask) {
        dao.deleteTaskItem(mapper.fromRepo(task))
    }
}