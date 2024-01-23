package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.DataDao
import city.zouitel.database.mapper.DataMapper
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.model.Data as OutData

class DataDataSourceImpl /*@Inject*/ constructor(
    private val dataDao: DataDao,
    private val dataMapper: DataMapper
): DataDataSource {
    override suspend fun addData(data: OutData) {
        dataDao.addNote(dataMapper.toLocal(data))
    }

    override suspend fun editData(data: OutData) {
        dataDao.editNote(dataMapper.toLocal(data))
    }

    override suspend fun deleteData(data: OutData) {
        dataDao.deleteNote(dataMapper.toLocal(data))
    }

    override suspend fun deleteAllTrashedData() {
        dataDao.deleteAllTrashedNotes()
    }
}