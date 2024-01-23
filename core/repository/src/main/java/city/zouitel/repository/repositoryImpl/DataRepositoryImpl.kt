package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Data as OutData
import city.zouitel.domain.repository.DataRepository
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.mapper.DataMapper

class DataRepositoryImpl /*@Inject*/ constructor(
    private val dataSource: DataDataSource,
    private val mapper: DataMapper
): DataRepository {
    override suspend fun addData(data: OutData) {
        dataSource.addData(mapper.toRepository(data))
    }

    override suspend fun editData(data: OutData) {
        dataSource.editData(mapper.toRepository(data))
    }

    override suspend fun deleteData(data: OutData) {
        dataSource.deleteData(mapper.toRepository(data))
    }

    override suspend fun deleteAllTrashedData() {
        dataSource.deleteAllTrashedData()
    }
}