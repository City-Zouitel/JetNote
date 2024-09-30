package city.zouitel.repository.datasource

import city.zouitel.repository.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {
    val getAllTaskItems: Flow<List<Task>>

    suspend fun addTaskItem(task: Task)

    suspend fun updateTaskItem(task: Task)

    suspend fun deleteTaskItem(task: Task)
}