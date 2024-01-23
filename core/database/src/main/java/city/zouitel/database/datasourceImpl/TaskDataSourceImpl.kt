package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TaskDao
import city.zouitel.database.mapper.TaskMapper
import city.zouitel.repository.datasource.TaskDataSource
import city.zouitel.repository.model.Task as OutTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskDataSourceImpl /*@Inject*/ constructor(
    private val taskDao: TaskDao,
    private val taskMapper: TaskMapper
): TaskDataSource {
    override val getAllTaskItems: Flow<List<OutTask>>
        get() = taskDao.getAllTaskItems().map { list ->
            list.map {
                taskMapper.readOnly(it)
            }
        }

    override suspend fun addTaskItem(task: OutTask) {
        taskDao.addTaskItem(taskMapper.toLocal(task))
    }

    override suspend fun updateTaskItem(task: OutTask) {
        taskDao.updateTaskItem(taskMapper.toLocal(task))
    }

    override suspend fun deleteTaskItem(task: OutTask) {
        taskDao.deleteTaskItem(taskMapper.toLocal(task))
    }
}