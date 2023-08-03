package city.zouitel.api

import kotlinx.coroutines.flow.Flow

interface ApiDataSource {

    fun getAllData(): Flow<ApiResult<List<Invalid>>>

    fun dataModifier(data: Invalid): Flow<ApiResult<Unit>>
}