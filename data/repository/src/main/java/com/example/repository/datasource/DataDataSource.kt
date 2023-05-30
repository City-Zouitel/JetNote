package com.example.repository.datasource

import com.example.repository.model.Data


interface DataDataSource {

    suspend fun addData(data: Data)

    suspend fun editData(data: Data)

    suspend fun deleteData(data: Data)

    suspend fun deleteAllTrashedData()

}