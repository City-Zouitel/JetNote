package city.zouitel.domain.repository

import city.zouitel.domain.model.Data as OutData

interface DataRepository {

    suspend fun addData(data: OutData)

    suspend fun editData(data: OutData)

    suspend fun deleteData(data: OutData)

    suspend fun deleteAllTrashedData()

}