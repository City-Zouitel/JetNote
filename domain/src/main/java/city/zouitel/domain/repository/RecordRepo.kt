package city.zouitel.domain.repository

import city.zouitel.domain.model.Record
import kotlinx.coroutines.flow.Flow


interface RecordRepo {
    val observeAll: Flow<List<Record>>

    suspend fun observeByUid(uid: String): Flow<List<Record>>

    suspend fun insert(record: Record)

    suspend fun delete(id: Long, path: String)

    suspend fun delete(uid: String)
}