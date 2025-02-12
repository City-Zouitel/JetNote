package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.RecordDao
import city.zouitel.database.mapper.RecordMapper
import city.zouitel.repository.datasource.RecordDataSource
import city.zouitel.repository.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecordDataSourceImpl(
    private val dao: RecordDao,
    private val mapper: RecordMapper
): RecordDataSource {
    override val observeAll: Flow<List<Record>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    override suspend fun observeByUid(uid: String): Flow<List<Record>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    override suspend fun insert(record: Record) {
        dao.insert(mapper.fromRepo(record))
    }

    override suspend fun delete(id: Long, path: String) {
        dao.delete(id, path)
    }

    override suspend fun delete(uid: String) {
        dao.delete(uid)
    }
}