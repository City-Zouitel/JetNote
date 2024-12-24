package city.zouitel.domain.repository

import kotlinx.coroutines.flow.Flow
import city.zouitel.domain.model.Task as OutTask

interface TaskRepository {

    val observeAll: Flow<List<OutTask>>

    fun observeByUid(uid: String): Flow<List<OutTask>>

    suspend fun insert(task: OutTask)

    suspend fun updateById(id: Long)

    suspend fun deleteById(id: Long)
}