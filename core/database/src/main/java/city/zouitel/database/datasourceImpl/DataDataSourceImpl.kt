package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.DataDao
import city.zouitel.database.mapper.DataMapper
import city.zouitel.repository.datasource.DataDataSource
import city.zouitel.repository.model.Data as OutData

class DataDataSourceImpl(
    private val dao: DataDao,
    private val mapper: DataMapper
): DataDataSource {
    override suspend fun addData(data: OutData) {
        dao.addNote(mapper.fromRepo(data))
    }

    override suspend fun editData(data: OutData) {
        dao.editNote(mapper.fromRepo(data))
    }

    override suspend fun deleteData(data: OutData) {
        dao.deleteNote(mapper.fromRepo(data))
    }

    override suspend fun deleteAllTrashedData() {
        dao.deleteAllTrashedNotes()
    }
}