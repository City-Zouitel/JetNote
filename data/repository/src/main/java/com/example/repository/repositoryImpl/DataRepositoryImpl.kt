package com.example.repository.repositoryImpl

import com.example.domain.model.Data as OutData
import com.example.domain.repository.DataRepository
import com.example.repository.datasource.DataDataSource
import com.example.repository.mapper.DataMapper
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
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