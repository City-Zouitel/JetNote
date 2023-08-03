package city.zouitel.api

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val dataSourceImpl: ApiDataSourceImpl
): ApiRepository {
    override fun getAllData(): Flow<ApiResult<List<Invalid>>> {
        return dataSourceImpl.getAllData()
    }

    override fun dataModifier(data: Invalid): Flow<ApiResult<Unit>> {
        return dataSourceImpl.dataModifier(data)
    }
}