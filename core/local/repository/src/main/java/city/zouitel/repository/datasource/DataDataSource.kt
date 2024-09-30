package city.zouitel.repository.datasource

import city.zouitel.repository.model.Data


interface DataDataSource {

    suspend fun addData(data: Data)

    suspend fun editData(data: Data)

    suspend fun deleteData(data: Data)

    suspend fun deleteAllTrashedData()

}