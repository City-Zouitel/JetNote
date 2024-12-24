package city.zouitel.repository.datasource

import city.zouitel.repository.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    val observeAll: Flow<List<Task>>

    fun observeByUid(uid: String): Flow<List<Task>>

    suspend fun insert(task: Task)

    suspend fun updateById(id: Long)

    suspend fun deleteById(id: Long)
}