package com.example.local.datasourceImpl

import com.example.local.dao.DataDao
import com.example.local.mapper.DataMapper
import com.example.repository.datasource.DataDataSource
import com.example.repository.model.Data
import javax.inject.Inject

class DataDataSourceImpl @Inject constructor(
    private val dataDao: DataDao,
    private val dataMapper: DataMapper
): DataDataSource {
    override suspend fun addData(data: Data) {
        dataDao.addNote(dataMapper.toLocal(data))
    }

    override suspend fun editData(data: Data) {
        dataDao.editNote(dataMapper.toLocal(data))
    }

    override suspend fun deleteData(data: Data) {
        dataDao.deleteNote(dataMapper.toLocal(data))
    }

    override suspend fun deleteAllTrashedData() {
        dataDao.deleteAllTrashedNotes()
    }
}