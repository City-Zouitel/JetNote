package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.RecordRepo
import city.zouitel.repository.datasource.RecordDataSource
import city.zouitel.repository.mapper.RecordMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Record as OutRecord

class RecordRepoImpl(
    private val dataSource: RecordDataSource,
    private val mapper: RecordMapper
): RecordRepo {
    override val observeAll: Flow<List<OutRecord>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    override suspend fun observeByUid(uid: String): Flow<List<OutRecord>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
    }

    override suspend fun insert(record: OutRecord) {
        dataSource.insert(mapper.fromDomain(record))
    }

    override suspend fun delete(id: Long, path: String) {
        dataSource.delete(id, path)
    }

    override suspend fun delete(uid: String) {
        dataSource.delete(uid)
    }
}